package com.nantianmen.protect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.nantianmen.protect.ui.MainViewModel
import com.nantianmen.protect.ui.NantianmenApp

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val pickApk = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let { viewModel.selectApk(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val state by viewModel.state.collectAsState()

                    NantianmenApp(
                        state = state,
                        onPickApk = { pickApk.launch(arrayOf("*/*")) },
                        onToggleFeature = { viewModel.toggleFeature(it) },
                        onSetAutoSign = { viewModel.setAutoSign(it) },
                        onStartProtect = { viewModel.startProtect() },
                        onSetTab = { viewModel.setTab(it) }
                    )
                }
            }
        }
    }
}
