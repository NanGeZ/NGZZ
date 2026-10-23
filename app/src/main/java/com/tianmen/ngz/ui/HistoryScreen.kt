package com.tianmen.ngz.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Launch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tianmen.ngz.ProtectViewModel
import com.tianmen.ngz.data.ProtectTask
import com.tianmen.ngz.ui.theme.Crimson
import com.tianmen.ngz.ui.theme.Jade
import com.tianmen.ngz.ui.theme.TextMuted
import com.tianmen.ngz.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val timeFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())

@Composable
fun HistoryScreen(viewModel: ProtectViewModel) {
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(20.dp))
        Text("HISTORY", style = MaterialTheme.typography.labelSmall, color = Jade)
        Spacer(Modifier.height(4.dp))
        Text("保护任务记录", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(16.dp))
        if (tasks.isEmpty()) {
            EmptyHistory()
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(tasks, key = { it.id }) { task -> TaskCard(task) }
                item { Spacer(Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
private fun EmptyHistory() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Outlined.Inventory2, contentDescription = null, tint = TextMuted, modifier = Modifier.size(48.dp))
            Spacer(Modifier.height(12.dp))
            Text("暂无任务记录", style = MaterialTheme.typography.titleSmall, color = TextSecondary)
            Spacer(Modifier.height(4.dp))
            Text("在「保护」页处理 APK 后将出现在这里", style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
    }
}

@Composable
private fun TaskCard(task: ProtectTask) {
    val context = LocalContext.current
    val failed = task.status.startsWith("失败")
    val accent = if (failed) Crimson else Jade
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .clickable(enabled = task.outputUri.isNotEmpty()) {
                runCatching {
                    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(android.net.Uri.parse(task.outputUri), "application/vnd.android.package-archive")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
                }
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(accent.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                if (failed) "!" else "✓",
                color = accent,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(task.inputPath, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(2.dp))
            Text(
                buildString {
                    append(task.packageName.ifEmpty { "—" })
                    append(" · ")
                    append(if (task.signed) "已签名" else "未签名")
                    append(" · ")
                    append(timeFormat.format(Date(task.createdAt)))
                },
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
            )
            Spacer(Modifier.height(2.dp))
            Text(task.status, style = MaterialTheme.typography.bodySmall, color = TextSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        if (task.outputUri.isNotEmpty()) {
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Outlined.Launch, contentDescription = "打开输出", tint = Jade, modifier = Modifier.size(18.dp))
        }
    }
}
