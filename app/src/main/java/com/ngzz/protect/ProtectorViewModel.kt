package com.ngzz.protect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ProtectorUiState(
    val switches: Map<String, Boolean> = Protection.entries.associate { it.id to it.enabled },
    val stage: String = "",
    val running: Boolean = false,
    val result: String? = null
)

class ProtectorViewModel(
    application: Application,
    private val engine: ProtectEngine = ProtectEngine(),
    private val payloadProvider: PayloadProvider = PayloadProvider(application)
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(ProtectorUiState())
    val state: StateFlow<ProtectorUiState> = _state

    fun toggle(id: String, enabled: Boolean) {
        _state.value = _state.value.copy(
            switches = _state.value.switches + (id to enabled)
        )
    }

    fun protect(apkPath: String) {
        if (_state.value.running) return
        _state.value = _state.value.copy(running = true, result = null, stage = "准备中")
        viewModelScope.launch {
            val outcome = withContext(Dispatchers.IO) {
                runCatching {
                    val payload = payloadProvider.extractAll(listOf("arm64-v8a", "armeabi-v7a"))
                    engine.protect(apkPath, _state.value.switches, { stage ->
                        _state.value = _state.value.copy(stage = stage)
                    }, payload)
                }
            }
            _state.value = _state.value.copy(
                running = false,
                stage = "",
                result = outcome.fold(
                    onSuccess = { "完成：$it" },
                    onFailure = { "失败：${it.message}" }
                )
            )
        }
    }
}
