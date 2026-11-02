package com.seedy.tool.ui

import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seedy.tool.engine.ApkProcessor
import com.seedy.tool.engine.Plan
import com.seedy.tool.engine.ProcessResult
import kotlinx.coroutines.launch

private data class PickedApk(val uri: Uri, val name: String, val size: Long)

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val processor = remember { ApkProcessor(context.applicationContext) }

    var picked by remember { mutableStateOf<PickedApk?>(null) }
    var plan by remember { mutableStateOf(Plan.PMS_HOOK) }
    var busy by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<ProcessResult?>(null) }
    var pendingName by remember { mutableStateOf("seedy.out.apk") }

    val openDoc = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            picked = PickedApk(uri, queryDisplayName(context, uri), querySize(context, uri))
            result = null
        }
    }

    val createDoc = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/vnd.android.package-archive")
    ) { uri ->
        val r = result as? ProcessResult.Success ?: return@rememberLauncherForActivityResult
        val pending = r.pendingFile ?: return@rememberLauncherForActivityResult
        if (uri != null) {
            scope.launch {
                val saved = processor.saveTo(uri, pending)
                result = if (saved != null) r.copy(savedTo = saved, pendingFile = null)
                else ProcessResult.Error("写入所选位置失败")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // ---------------------------------------------------------- 选 APK
        SectionTitle("1. 选择 APK")
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val p = picked
                if (p == null) {
                    Text("未选择文件", style = MaterialTheme.typography.bodyMedium)
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Android, null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(p.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            Text(formatSize(p.size), style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        openDoc.launch(arrayOf("*/*"))
                    }) { Text(if (picked == null) "选择本地 APK" else "更换 APK") }
                }
            }
        }

        // ------------------------------------------------------ 过签方案
        SectionTitle("2. 过签方案")
        Plan.entries.forEach { item ->
            PlanCard(item, selected = item == plan, onSelect = {
                plan = item
                result = null
            })
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f)
            )
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Warning, null, tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("共同局限", style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold)
                }
                Plan.LIMITATIONS.forEach {
                    Text("· $it", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // ---------------------------------------------------------- 处理
        SectionTitle("3. 过签处理")
        Button(
            onClick = {
                val apk = picked ?: return@Button
                busy = true
                progress = "准备中…"
                result = null
                scope.launch {
                    val r = processor.process(apk.uri, plan) { progress = it }
                    if (r is ProcessResult.Success && r.savedTo == null) {
                        pendingName = r.outputName
                        createDoc.launch(r.outputName)
                    }
                    result = r
                    busy = false
                }
            },
            enabled = picked != null && !busy,
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            if (busy) {
                CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                Spacer(Modifier.width(8.dp))
                Text(progress)
            } else {
                Text("开始过签处理（输出不签名）")
            }
        }
        if (busy) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }

        // ---------------------------------------------------------- 结果
        result?.let { r ->
            ResultCard(r)
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
}

@Composable
private fun PlanCard(item: Plan, selected: Boolean, onSelect: () -> Unit) {
    Card(
        onClick = onSelect,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        border = if (selected) CardDefaults.outlinedCardBorder() else null,
    ) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.Top) {
            RadioButton(selected = selected, onClick = onSelect)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(item.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(item.desc, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("注入：${item.injects.joinToString("、")}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun ResultCard(r: ProcessResult) {
    when (r) {
        is ProcessResult.Error -> Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("处理失败", style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                Text(r.message, style = MaterialTheme.typography.bodySmall)
            }
        }

        is ProcessResult.Success -> Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.CheckCircle, null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.width(6.dp))
                    Text("处理完成（未签名，请自行签名）",
                        style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                }
                InfoLine("输出文件", r.outputName)
                InfoLine("保存位置", r.savedTo ?: "待选择目录保存")
                InfoLine("签名方案", r.analysis.schemeText)
                InfoLine("原始证书", "${r.analysis.certs.size} 张")
                r.analysis.certs.forEachIndexed { i, c ->
                    Text("SHA-256 #${i + 1}", style = MaterialTheme.typography.labelSmall)
                    Text(c.sha256, style = MaterialTheme.typography.labelSmall,
                        fontFamily = FontFamily.Monospace)
                }
                InfoLine("已注入", r.injected.joinToString("、"))
            }
        }
    }
}

@Composable
private fun InfoLine(label: String, value: String) {
    Row {
        Text("$label：", style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
    }
}

private fun queryDisplayName(context: android.content.Context, uri: Uri): String {
    context.contentResolver.query(uri, null, null, null, null)?.use { c ->
        if (c.moveToFirst()) {
            val idx = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (idx >= 0) return c.getString(idx)
        }
    }
    return uri.lastPathSegment ?: "unknown.apk"
}

private fun querySize(context: android.content.Context, uri: Uri): Long {
    context.contentResolver.query(uri, null, null, null, null)?.use { c ->
        if (c.moveToFirst()) {
            val idx = c.getColumnIndex(OpenableColumns.SIZE)
            if (idx >= 0) return c.getLong(idx)
        }
    }
    return 0L
}

private fun formatSize(bytes: Long): String = when {
    bytes >= 1024 * 1024 -> "%.1f MB".format(bytes / 1024.0 / 1024.0)
    bytes > 0 -> "%.1f KB".format(bytes / 1024.0)
    else -> "未知大小"
}
