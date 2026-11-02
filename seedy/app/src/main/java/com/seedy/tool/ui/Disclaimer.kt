package com.seedy.tool.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

const val DISCLAIMER_TITLE = "免责声明"

val DISCLAIMER_TEXT = """
Seedy 是一款签名校验测试工具，仅供学习与授权研究。

一、用途限制
本工具及其过签处理能力仅可用于对您本人拥有或已获明确授权的应用程序进行签名校验机制的测试与研究。严禁将本工具用于破解、盗版、篡改、二次分发他人应用程序，或任何违反法律法规、侵犯他人合法权益的用途。

二、风险自负
本工具对 APK 的处理（注入签名记录、载荷等）会改变文件内容，可能导致目标应用无法运行、功能异常或数据丢失。处理后的 APK 为未签名文件，须由您自行签名后方可安装。因使用本工具产生的一切后果与风险由使用者自行承担，开发者不承担任何责任。

三、功能边界
本工具不支持、也无法绕过：native 层自校验、APK 完整性/哈希校验、Play Integrity / SafetyNet 等服务端校验。请勿对超出授权范围的场景抱有预期。

四、无担保
本工具按“现状”提供，不作任何明示或默示的担保，包括但不限于适销性、特定用途适用性。在适用法律允许的最大范围内，开发者不对任何直接或间接损失负责。

五、隐私
本工具完全在本地运行，不需要 root 权限，不收集、不上传任何数据。

如果您不同意上述条款，请立即停止使用本工具。
""".trimIndent()

@Composable
fun DisclaimerDialog(
    requireAgree: Boolean,
    onAgree: () -> Unit,
    onDismiss: () -> Unit,
) {
    var checked by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = { if (!requireAgree) onDismiss() },
        title = { Text(DISCLAIMER_TITLE, fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(DISCLAIMER_TEXT, style = MaterialTheme.typography.bodySmall)
                if (requireAgree) {
                    Spacer(Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = checked, onCheckedChange = { checked = it })
                        Spacer(Modifier.width(4.dp))
                        Text("我已完整阅读并同意以上免责声明", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        },
        confirmButton = {
            if (requireAgree) {
                Button(enabled = checked, onClick = onAgree) { Text("同意并开始使用") }
            } else {
                Button(onClick = onDismiss) { Text("关闭") }
            }
        },
        dismissButton = if (requireAgree) {
            null
        } else {
            { OutlinedButton(onClick = onDismiss) { Text("返回") } }
        },
    )
}
