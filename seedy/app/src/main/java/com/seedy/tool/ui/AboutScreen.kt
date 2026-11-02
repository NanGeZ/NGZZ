package com.seedy.tool.ui

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private const val FEEDBACK_EMAIL = "xnzyw6@gmail.com"

@Composable
fun AboutScreen(onShowDisclaimer: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                Text("Seedy", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("v1.0 · com.seedy.tool",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Text(
                    "一款签名校验测试工具，仅供学习与授权研究。",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        AboutItem(
            icon = Icons.Outlined.Info,
            title = "软件介绍",
            body = "Seedy 用于对 APK 的签名校验机制进行测试与研究：提取原始签名证书并写入签名记录，" +
                "可选注入免 root 运行时载荷（进程内 PMS hook），输出处理后 APK 供授权研究使用。" +
                "全程免 root，完全本地运行，不收集任何数据。",
        )

        AboutItem(
            icon = Icons.Outlined.Email,
            title = "问题反馈",
            body = FEEDBACK_EMAIL,
        ) {
            OutlinedButton(onClick = {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$FEEDBACK_EMAIL")).apply {
                    putExtra(Intent.EXTRA_SUBJECT, "Seedy 反馈")
                }
                runCatching { context.startActivity(intent) }
            }) { Text("发送邮件") }
        }

        AboutItem(
            icon = Icons.Outlined.Shield,
            title = "免责声明",
            body = "使用本工具即表示您已阅读并同意免责声明。请仅在授权范围内使用。",
        ) {
            OutlinedButton(onClick = onShowDisclaimer) { Text("查看免责声明") }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun AboutItem(
    icon: ImageVector,
    title: String,
    body: String,
    action: (@Composable () -> Unit)? = null,
) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            }
            Text(body, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            action?.invoke()
        }
    }
}
