package com.ngzz.protect

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    var showHistory by remember { mutableStateOf(false) }
    var showClearConfirm by remember { mutableStateOf(false) }

    val pickApk = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri -> apkUri = uri }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Header(showHistory) { showHistory = it }

        if (showHistory) {
            HistorySection(
                history = state.history,
                onClear = { showClearConfirm = true }
            )
        } else {
            ProtectionSection(state, viewModel)

            Spacer(Modifier.height(16.dp))
            ApkPickerSection(apkUri, pickApk)

            Spacer(Modifier.height(12.dp))
            ActionSection(
                state = state,
                apkUri = apkUri,
                onProtect = { uri -> viewModel.protect(uri) }
            )

            state.progress?.let { progress ->
                Spacer(Modifier.height(16.dp))
                ProgressSection(progress)
            }

            state.result?.let { result ->
                Spacer(Modifier.height(12.dp))
                ResultCard(result)
            }

            state.error?.let { error ->
                Spacer(Modifier.height(12.dp))
                ErrorCard(error) { viewModel.dismissError() }
            }
        }
    }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("清空历史") },
            text = { Text("确定要清空所有处理历史吗？") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearHistory()
                    showClearConfirm = false
                }) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) { Text("取消") }
            }
        )
    }
}

@Composable
fun Header(showHistory: Boolean, onToggleHistory: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("南天门", style = MaterialTheme.typography.headlineMedium)
            Text(
                "APK 综合保护工具箱 · 纯本地",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        TextButton(onClick = { onToggleHistory(!showHistory) }) {
            Text(if (showHistory) "返回" else "历史")
        }
    }
}

@Composable
fun ProtectionSection(state: ProtectorUiState, viewModel: ProtectorViewModel) {
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
}

@Composable
fun ApkPickerSection(apkUri: Uri?, pickApk: androidx.activity.compose.ManagedActivityResultLauncher<Array<String>, Uri?>) {
    OutlinedButton(
        onClick = { pickApk.launch(arrayOf("application/vnd.android.package-archive", "*/*")) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            apkUri?.lastPathSegment?.substringAfterLast('/') ?: "选择目标 APK",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ActionSection(
    state: ProtectorUiState,
    apkUri: Uri?,
    onProtect: (Uri) -> Unit
) {
    Button(
        onClick = { apkUri?.let { onProtect(it) } },
        enabled = apkUri != null && !state.running,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (state.running) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
            Spacer(Modifier.width(8.dp))
            Text("处理中…")
        } else {
            Text("开始保护")
        }
    }
}

@Composable
fun ProgressSection(progress: ProtectProgress) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    progress.stage,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    "${progress.step}/${progress.totalSteps}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress.percentage / 100f },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            StageIndicators(progress)
        }
    }
}

@Composable
fun StageIndicators(progress: ProtectProgress) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ProtectStage.all.forEach { stage ->
            val isDone = ProtectStage.all.indexOf(stage) < progress.step
            val isCurrent = stage.title == progress.stage
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = when {
                        isDone -> Icons.Default.CheckCircle
                        isCurrent -> Icons.Default.Info
                        else -> Icons.Default.Close
                    },
                    contentDescription = stage.title,
                    modifier = Modifier.size(16.dp),
                    tint = when {
                        isDone -> Color(0xFF4CAF50)
                        isCurrent -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline
                    }
                )
                Text(
                    stage.title.take(2),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isCurrent) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ResultCard(result: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text("处理成功", style = MaterialTheme.typography.titleSmall)
                Text(
                    result.removePrefix("完成："),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ErrorCard(error: String, onDismiss: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("处理失败", style = MaterialTheme.typography.titleSmall)
                Text(error, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "关闭")
            }
        }
    }
}

@Composable
fun HistorySection(history: List<ProtectRecord>, onClear: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("处理历史", style = MaterialTheme.typography.titleMedium)
        if (history.isNotEmpty()) {
            IconButton(onClick = onClear) {
                Icon(Icons.Default.Delete, contentDescription = "清空")
            }
        }
    }

    if (history.isEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                "暂无处理记录",
                modifier = Modifier.padding(24.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        history.forEach { record ->
            HistoryItem(record)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun HistoryItem(record: ProtectRecord) {
    val dateFormat = remember { SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (record.success) Icons.Default.CheckCircle else Icons.Default.Close,
                contentDescription = null,
                tint = if (record.success) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    record.apkName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    if (record.success) record.outputPath.substringAfterLast('/')
                    else record.errorMessage ?: "未知错误",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                dateFormat.format(Date(record.timestamp)),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
