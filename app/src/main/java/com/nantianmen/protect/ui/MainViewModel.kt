package com.nantianmen.protect.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nantianmen.protect.core.ApkAnalyzer
import com.nantianmen.protect.core.model.*
import com.nantianmen.protect.core.protect.ProtectPipeline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

data class UiState(
    val selectedApkUri: Uri? = null,
    val selectedApkName: String = "",
    val analysis: ApkAnalysis? = null,
    val selectedFeatures: Set<ProtectFeature> = emptySet(),
    val autoSign: Boolean = true,
    val isProtecting: Boolean = false,
    val progress: ProtectProgress = ProtectProgress(),
    val result: ProtectResult? = null,
    val tab: Int = 0,
    /** 文件输出目录（展示给用户） */
    val outputDir: String = MainViewModel.PUBLIC_OUTPUT_DIR,
    /** 是否已获得目标输出目录的写入权限 */
    val hasStorageAccess: Boolean = false,
    /** 需要引导用户授权（仅提示用） */
    val needStoragePrompt: Boolean = false
)

class MainViewModel(app: Application) : AndroidViewModel(app) {

    companion object {
        /** 文件输出目录（公共存储，用户可直接访问） */
        const val PUBLIC_OUTPUT_DIR = "/storage/emulated/0/Nantianmen"
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refreshStorage()
    }

    /** 从设置页/授权页返回后刷新存储权限与输出目录 */
    fun refreshStorage() {
        val granted = hasStorageAccess()
        _state.value = _state.value.copy(
            hasStorageAccess = granted,
            outputDir = resolveOutputDir().absolutePath,
            needStoragePrompt = false
        )
    }

    /** 是否已具备写入目标公共目录的权限 */
    fun hasStorageAccess(): Boolean {
        val ctx = getApplication<Application>()
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            android.os.Environment.isExternalStorageManager()
        } else {
            androidx.core.content.ContextCompat.checkSelfPermission(
                ctx, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
    }

    /** 优先公共目录 /storage/emulated/0/Nantianmen；不可写时回退到应用外部私有目录 */
    fun resolveOutputDir(): File {
        val preferred = File(PUBLIC_OUTPUT_DIR)
        if (isWritableDir(preferred)) return preferred
        val ctx = getApplication<Application>()
        val fallback = File(ctx.getExternalFilesDir(null) ?: ctx.filesDir, "Nantianmen")
        fallback.mkdirs()
        return fallback
    }

    private fun isWritableDir(dir: File): Boolean {
        return try {
            dir.mkdirs()
            val probe = File(dir, ".nantianmen_write_probe")
            probe.outputStream().use { it.write(0) }
            probe.delete()
            true
        } catch (_: Throwable) {
            false
        }
    }

    fun selectApk(uri: Uri) {
        val name = uri.lastPathSegment?.substringAfterLast('/') ?: "unknown.apk"
        _state.value = _state.value.copy(selectedApkUri = uri, selectedApkName = name, analysis = null, result = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = copyUriToFile(uri)
                if (file != null) {
                    val analysis = ApkAnalyzer.analyze(file)
                    _state.value = _state.value.copy(analysis = analysis)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun toggleFeature(f: ProtectFeature) {
        val cur = _state.value.selectedFeatures
        _state.value = _state.value.copy(
            selectedFeatures = if (f in cur) cur - f else cur + f
        )
    }

    fun setAutoSign(b: Boolean) {
        _state.value = _state.value.copy(autoSign = b)
    }

    fun setTab(tab: Int) {
        _state.value = _state.value.copy(tab = tab)
    }

    fun startProtect() {
        val uri = _state.value.selectedApkUri ?: return
        val features = _state.value.selectedFeatures
        if (features.isEmpty()) return

        _state.value = _state.value.copy(isProtecting = true, result = null, needStoragePrompt = false)
        viewModelScope.launch(Dispatchers.IO) {
            // 输出目录不可写时：未授权则引导去授权，已授权则回退私有目录
            val preferredWritable = isWritableDir(File(PUBLIC_OUTPUT_DIR))
            if (!preferredWritable && !hasStorageAccess()) {
                _state.value = _state.value.copy(
                    isProtecting = false, hasStorageAccess = false, needStoragePrompt = true
                )
                return@launch
            }
            val outputDir = resolveOutputDir()
            _state.value = _state.value.copy(outputDir = outputDir.absolutePath)

            val apkFile = copyUriToFile(uri)
            if (apkFile == null) {
                _state.value = _state.value.copy(isProtecting = false)
                return@launch
            }
            ProtectPipeline.protect(
                apkFile, outputDir,
                ProtectOptions(features, _state.value.autoSign),
                getApplication<Application>().applicationInfo.sourceDir
            ) { progress ->
                _state.value = _state.value.copy(progress = progress)
            }.collect { result ->
                _state.value = _state.value.copy(result = result, isProtecting = false)
            }
        }
    }

    private fun copyUriToFile(uri: Uri): File? {
        return try {
            val ctx = getApplication<Application>()
            val file = File(ctx.cacheDir, "input_${System.currentTimeMillis()}.apk")
            ctx.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
            file
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        }
    }
}
