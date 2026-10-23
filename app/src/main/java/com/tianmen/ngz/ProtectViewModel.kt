package com.tianmen.ngz

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tianmen.ngz.data.NgzDatabase
import com.tianmen.ngz.data.ProtectTask
import com.tianmen.ngz.engine.EngineException
import com.tianmen.ngz.engine.guard.GuardConfig
import com.tianmen.ngz.engine.guard.GuardHit
import com.tianmen.ngz.engine.pipeline.KeystoreImporter
import com.tianmen.ngz.engine.pipeline.ProtectResult
import com.tianmen.ngz.engine.pipeline.ProtectionConfig
import com.tianmen.ngz.engine.pipeline.ProtectionPipeline
import com.tianmen.ngz.settings.NgzSettings
import com.tianmen.ngz.settings.SettingsRepository
import com.tianmen.ngz.util.FileImporter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

data class ProtectionOptions(
    val signature: Boolean = true,
    val antiDebug: Boolean = true,
    val packageName: Boolean = true,
    val capture: Boolean = true,
    val root: Boolean = true,
    val network: Boolean = false,
    val expiry: Boolean = false,
    val expiryEpochMillis: Long = 0L,
    val exitOnHit: Boolean = true,
)

enum class ProcessStatus { IDLE, RUNNING, SUCCESS, ERROR }

data class ProcessUiState(
    val status: ProcessStatus = ProcessStatus.IDLE,
    val inputName: String = "",
    val inputSizeLabel: String = "",
    val lastOutputUri: String = "",
    val message: String = "",
)

@HiltViewModel
class ProtectViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository,
    private val database: NgzDatabase,
) : ViewModel() {

    private val _processState = MutableStateFlow(ProcessUiState())
    val processState: StateFlow<ProcessUiState> = _processState.asStateFlow()

    private val _settings = MutableStateFlow(NgzSettings.DEFAULT)
    val settings: StateFlow<NgzSettings> = _settings.asStateFlow()

    private val _settingsMessage = MutableStateFlow("")
    val settingsMessage: StateFlow<String> = _settingsMessage.asStateFlow()

    private val _selectedApk = MutableStateFlow<File?>(null)
    val selectedApk: StateFlow<File?> = _selectedApk.asStateFlow()

    val tasks = database.protectTaskDao().observeAll()

    init {
        refreshSettings()
    }

    fun refreshSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            _settings.value = settingsRepository.current()
        }
    }

    fun importApk(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = FileImporter.copyToDir(
                    context, uri, File(context.filesDir, "apk_in"), "imported.apk"
                )
                _selectedApk.value = file
                _processState.update {
                    it.copy(
                        status = ProcessStatus.IDLE,
                        inputName = file.name,
                        inputSizeLabel = formatSize(file.length()),
                        message = "",
                    )
                }
            } catch (e: Exception) {
                _processState.update {
                    it.copy(status = ProcessStatus.ERROR, message = "导入 APK 失败: ${e.message}")
                }
            }
        }
    }

    fun importKeystore(uri: Uri, alias: String, keyPassword: String, storePassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = FileImporter.copyToDir(
                    context, uri, File(context.filesDir, "keystore"), "imported.jks"
                )
                KeystoreImporter().importKeystore(file, file, alias, keyPassword, storePassword)
                val updated = settingsRepository.current().copy(
                    keystoreUri = uri.toString(),
                    keystorePath = file.absolutePath,
                    keyAlias = alias,
                    keyPassword = keyPassword,
                    storePassword = storePassword,
                )
                settingsRepository.update(updated)
                _settings.value = updated
                _settingsMessage.value = "签名导入成功: ${file.name} ($alias)"
            } catch (e: Exception) {
                _settingsMessage.value = "签名导入失败: ${e.message}"
            }
        }
    }

    fun updateSettings(settings: NgzSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.update(settings)
            _settings.value = settings
            _settingsMessage.value = "设置已保存"
        }
    }

    fun process(options: ProtectionOptions, outputName: String) {
        val input = _selectedApk.value ?: return
        if (_processState.value.status == ProcessStatus.RUNNING) return
        _processState.update {
            it.copy(status = ProcessStatus.RUNNING, message = "处理中…", lastOutputUri = "")
        }
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    runPipeline(input, options, outputName)
                }
                _processState.update {
                    it.copy(
                        status = ProcessStatus.SUCCESS,
                        message = "处理完成: ${result.packageName}" +
                            if (result.signed) " · 已签名" else " · 未签名",
                        lastOutputUri = result.outputUri,
                    )
                }
                saveTask(input, result, options)
            } catch (e: Exception) {
                val reason = if (e is EngineException) e.message else "处理失败: ${e.message}"
                _processState.update { it.copy(status = ProcessStatus.ERROR, message = reason ?: "处理失败") }
                saveFailedTask(input, reason ?: "处理失败")
            }
        }
    }

    private fun runPipeline(input: File, options: ProtectionOptions, outputName: String): PipelineOutcome {
        val settings = settingsRepository.current()
        val guardConfig = GuardConfig.Builder()
            .checkSignature(options.signature)
            .antiDebug(options.antiDebug)
            .checkPackageName(options.packageName)
            .checkCapture(options.capture)
            .checkRoot(options.root)
            .checkNetwork(options.network)
            .checkExpiry(options.expiry)
            .expiryEpochMillis(if (options.expiry) options.expiryEpochMillis else 0L)
            .exitOnHit(options.exitOnHit)
            .build()
        val internalOut = File(File(context.filesDir, "apk_out"), outputName)
        val configBuilder = ProtectionConfig.Builder()
            .inputApk(input)
            .outputApk(internalOut)
            .autoSign(settings.autoSign && settings.keystorePath.isNotEmpty())
            .injectGuard(true)
        if (settings.autoSign && settings.keystorePath.isNotEmpty()) {
            configBuilder
                .keystore(File(settings.keystorePath))
                .keyAlias(settings.keyAlias)
                .keyPassword(settings.keyPassword)
                .storePassword(settings.storePassword)
        }
        val result: ProtectResult = ProtectionPipeline().run(configBuilder.build())
        val outputUri = exportToDownloads(result.outputApk, outputName)
        return PipelineOutcome(result.packageName, result.signed, result.outputApk.absolutePath, outputUri)
    }

    private fun exportToDownloads(file: File, outputName: String): String {
        val resolver = context.contentResolver
        val collection = android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val existing = resolver.query(
            collection,
            arrayOf(android.provider.MediaStore.MediaColumns._ID),
            "${android.provider.MediaStore.MediaColumns.DISPLAY_NAME} = ?",
            arrayOf(outputName),
            null,
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                android.content.ContentUris.withAppendedId(collection, cursor.getLong(0))
            } else null
        }
        val uri = existing ?: resolver.insert(collection, android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, outputName)
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "application/vnd.android.package-archive")
        }) ?: throw EngineException("无法创建输出文件")
        resolver.openOutputStream(uri, "wt")?.use { out ->
            file.inputStream().use { it.copyTo(out) }
        } ?: throw EngineException("无法写入输出文件")
        return uri.toString()
    }

    private fun saveTask(input: File, result: PipelineOutcome, options: ProtectionOptions) {
        viewModelScope.launch(Dispatchers.IO) {
            database.protectTaskDao().insert(
                ProtectTask(
                    inputPath = input.name,
                    outputPath = result.outputPath,
                    outputUri = result.outputUri,
                    packageName = result.packageName,
                    status = statusOf(options),
                    signed = result.signed,
                    createdAt = System.currentTimeMillis(),
                )
            )
        }
    }

    private fun saveFailedTask(input: File, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.protectTaskDao().insert(
                ProtectTask(
                    inputPath = input.name,
                    outputPath = "",
                    outputUri = "",
                    packageName = "",
                    status = "失败: $reason",
                    signed = false,
                    createdAt = System.currentTimeMillis(),
                )
            )
        }
    }

    private fun statusOf(options: ProtectionOptions): String {
        val hits = mutableListOf<GuardHit>()
        if (options.signature) hits.add(GuardHit.SIGNATURE)
        if (options.antiDebug) hits.add(GuardHit.DEBUG)
        if (options.packageName) hits.add(GuardHit.PACKAGE_NAME)
        if (options.capture) hits.add(GuardHit.CAPTURE)
        if (options.root) hits.add(GuardHit.ROOT)
        if (options.network) hits.add(GuardHit.NETWORK)
        if (options.expiry) hits.add(GuardHit.EXPIRY)
        return if (hits.isEmpty()) "仅重打包" else "保护: " + hits.joinToString("/") { it.name }
    }

    private fun formatSize(bytes: Long): String = when {
        bytes >= 1 shl 30 -> "%.1f GB".format(bytes / (1 shl 30).toDouble())
        bytes >= 1 shl 20 -> "%.1f MB".format(bytes / (1 shl 20).toDouble())
        bytes >= 1 shl 10 -> "%.1f KB".format(bytes / (1 shl 10).toDouble())
        else -> "$bytes B"
    }

    private data class PipelineOutcome(
        val packageName: String,
        val signed: Boolean,
        val outputPath: String,
        val outputUri: String,
    )
}
