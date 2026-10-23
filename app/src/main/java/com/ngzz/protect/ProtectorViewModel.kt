package com.ngzz.protect

import android.app.Application
import android.net.Uri
import android.provider.DocumentsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ProtectorUiState(
    val switches: Map<String, Boolean> = Protection.entries.associate { it.id to it.enabled },
    val progress: ProtectProgress? = null,
    val running: Boolean = false,
    val result: String? = null,
    val error: String? = null,
    val history: List<ProtectRecord> = emptyList(),
    val settings: AppSettings = AppSettings()
)

class ProtectorViewModel(application: Application) : AndroidViewModel(application) {

    private val engine: ProtectEngine = ProtectEngine()
    private val payloadProvider: PayloadProvider = PayloadProvider(application)
    private val history: ProtectHistory = ProtectHistory(application)
    private val settingsStore: SettingsStore = SettingsStore(application)

    private val _state = MutableStateFlow(
        ProtectorUiState(
            history = history.load(),
            settings = settingsStore.load()
        )
    )
    val state: StateFlow<ProtectorUiState> = _state

    fun toggle(id: String, enabled: Boolean) {
        _state.value = _state.value.copy(
            switches = _state.value.switches + (id to enabled)
        )
    }

    fun setAutoSign(enabled: Boolean) = updateSettings { it.copy(autoSign = enabled) }

    fun setExpiry(millis: Long) = updateSettings { it.copy(expiryMillis = millis) }

    fun updateSignerCredentials(alias: String, storePass: String, keyPass: String) =
        updateSettings { it.copy(alias = alias, storePassword = storePass, keyPassword = keyPass) }

    fun importKeystore(uri: Uri) {
        viewModelScope.launch {
            runCatching { settingsStore.importKeystore(uri) }
                .onFailure { e ->
                    val err = ProtectError.from(e)
                    _state.value = _state.value.copy(error = err.message ?: "导入失败")
                }
                .onSuccess {
                    _state.value = _state.value.copy(settings = settingsStore.load())
                }
        }
    }

    fun resetToBuiltinSigner() {
        settingsStore.resetToBuiltinSigner()
        _state.value = _state.value.copy(settings = settingsStore.load())
    }

    fun setOutputTree(uri: Uri?) {
        viewModelScope.launch {
            if (uri == null) {
                updateSettings { it.copy(outputTreeUri = "") }
                return@launch
            }
            runCatching {
                val flags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                viewModelScope.launch(Dispatchers.IO) {
                    getApplication<Application>().contentResolver
                        .takePersistableUriPermission(uri, flags)
                }
                DocumentsContract.isDocumentUri(getApplication(), uri)
            }.onSuccess {
                updateSettings { s -> s.copy(outputTreeUri = uri.toString()) }
            }.onFailure { e ->
                _state.value = _state.value.copy(error = "无法使用所选目录: ${e.message}")
            }
        }
    }

    private fun updateSettings(transform: (AppSettings) -> AppSettings) {
        val newSettings = transform(_state.value.settings)
        settingsStore.save(newSettings)
        _state.value = _state.value.copy(settings = newSettings)
    }

    fun protect(uri: Uri) {
        if (_state.value.running) return
        _state.value = _state.value.copy(
            running = true,
            result = null,
            error = null,
            progress = ProtectProgress("准备中", 0, ProtectStage.all.size)
        )

        viewModelScope.launch {
            val outcome = withContext(Dispatchers.IO) {
                runCatching {
                    val apkPath = copyUriToCache(uri)
                    val settings = _state.value.settings
                    val payload = payloadProvider.extractAll(listOf("arm64-v8a", "armeabi-v7a"))
                    val outputPath = engine.protect(
                        apkPath = apkPath,
                        switches = _state.value.switches,
                        expiryMillis = settings.expiryMillis,
                        autoSign = settings.autoSign,
                        signerConfig = settingsStore.signerConfig(settings),
                        outputDir = getApplication<Application>().filesDir,
                        onProgress = { progress ->
                            _state.value = _state.value.copy(progress = progress)
                        },
                        payload = payload
                    )
                    exportToTree(outputPath)
                }
            }

            outcome.fold(
                onSuccess = { outputPath ->
                    val record = ProtectRecord(
                        apkName = uri.lastPathSegment ?: "unknown.apk",
                        outputPath = outputPath,
                        success = true
                    )
                    val updatedHistory = history.add(record)
                    _state.value = _state.value.copy(
                        running = false,
                        progress = _state.value.progress?.copy(isComplete = true),
                        result = outputPath,
                        history = updatedHistory
                    )
                },
                onFailure = { throwable ->
                    val protectError = ProtectError.from(throwable)
                    val record = ProtectRecord(
                        apkName = uri.lastPathSegment ?: "unknown.apk",
                        outputPath = "",
                        success = false,
                        errorMessage = protectError.message
                    )
                    val updatedHistory = history.add(record)
                    _state.value = _state.value.copy(
                        running = false,
                        progress = null,
                        error = protectError.message ?: "未知错误",
                        history = updatedHistory
                    )
                }
            )
        }
    }

    private fun exportToTree(outputPath: String): String {
        val treeUri = _state.value.settings.outputTreeUri
        if (treeUri.isEmpty()) return outputPath
        val context = getApplication<Application>()
        return try {
            val tree = Uri.parse(treeUri)
            val name = outputPath.substringAfterLast('/')
            val docUri = DocumentsContract.createDocument(
                context.contentResolver,
                tree,
                "application/vnd.android.package-archive",
                name
            ) ?: return outputPath
            context.contentResolver.openOutputStream(docUri)?.use { out ->
                java.io.File(outputPath).inputStream().use { it.copyTo(out) }
            } ?: return outputPath
            "$name（已复制到所选目录）"
        } catch (e: Exception) {
            "$outputPath（复制到自定义目录失败: ${e.message}）"
        }
    }

    fun clearHistory() {
        history.clear()
        _state.value = _state.value.copy(history = emptyList())
    }

    fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }

    private fun copyUriToCache(uri: Uri): String {
        val context = getApplication<Application>()
        val input = context.contentResolver.openInputStream(uri)
            ?: throw ProtectError.ApkReadFailed()
        val outFile = java.io.File(context.cacheDir, "input_${System.currentTimeMillis()}.apk")
        input.use { ins ->
            outFile.outputStream().use { outs ->
                ins.copyTo(outs)
            }
        }
        cleanupOldCache()
        return outFile.absolutePath
    }

    private fun cleanupOldCache() {
        val cutoff = System.currentTimeMillis() - 3600_000L
        getApplication<Application>().cacheDir.listFiles()?.forEach { f ->
            if (f.name.startsWith("input_") && f.lastModified() < cutoff) f.delete()
        }
    }
}
