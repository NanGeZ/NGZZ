package com.ngzz.protect

import android.app.Application
import android.net.Uri
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
    val history: List<ProtectRecord> = emptyList()
)

class ProtectorViewModel(
    application: Application,
    private val engine: ProtectEngine = ProtectEngine(),
    private val payloadProvider: PayloadProvider = PayloadProvider(application),
    private val history: ProtectHistory = ProtectHistory(application)
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(ProtectorUiState(history = history.load()))
    val state: StateFlow<ProtectorUiState> = _state

    fun toggle(id: String, enabled: Boolean) {
        _state.value = _state.value.copy(
            switches = _state.value.switches + (id to enabled)
        )
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
                    val payload = payloadProvider.extractAll(listOf("arm64-v8a", "armeabi-v7a"))
                    engine.protect(apkPath, _state.value.switches, { progress ->
                        _state.value = _state.value.copy(progress = progress)
                    }, payload)
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
                        result = "完成：$outputPath",
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
        return outFile.absolutePath
    }
}
