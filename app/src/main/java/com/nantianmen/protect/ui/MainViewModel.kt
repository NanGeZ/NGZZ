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
    val tab: Int = 0
)

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

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

        _state.value = _state.value.copy(isProtecting = true, result = null)
        viewModelScope.launch {
            val apkFile = copyUriToFile(uri)
            if (apkFile == null) {
                _state.value = _state.value.copy(isProtecting = false)
                return@launch
            }
            val outputDir = File(getApplication<Application>().filesDir, "protected")
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
