package com.ngzz.protect

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ProtectorScreen()
                }
            }
        }
    }
}

@Composable
fun ProtectorScreen(viewModel: ProtectorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    var apkUri by remember { mutableStateOf<Uri?>(null) }

    val pickApk = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri -> apkUri = uri }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("南天门", style = MaterialTheme.typography.headlineMedium)
        Text(
            "APK 综合保护工具箱 · 纯本地",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))

        Protection.entries.forEach { p ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(p.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        p.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = state.switches[p.id] ?: true,
                    onCheckedChange = { viewModel.toggle(p.id, it) }
                )
            }
            HorizontalDivider()
        }

        Spacer(Modifier.height(16.dp))
        OutlinedButton(
            onClick = { pickApk.launch(arrayOf("application/vnd.android.package-archive", "*/*")) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(apkUri?.lastPathSegment ?: "选择目标 APK")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                apkUri?.let { uri ->
                    viewModel.protect(uri.toString())
                }
            },
            enabled = apkUri != null && !state.running,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.running) "处理中…" else "开始保护")
        }

        if (state.stage.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(state.stage, style = MaterialTheme.typography.bodyMedium)
        }
        state.result?.let {
            Spacer(Modifier.height(4.dp))
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
