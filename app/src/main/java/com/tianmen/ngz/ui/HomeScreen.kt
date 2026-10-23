package com.tianmen.ngz.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ProtectionToggle(val label: String, val enabled: Boolean)

@Composable
fun HomeScreen(onProcess: () -> Unit) {
    var signature by remember { mutableStateOf(false) }
    var antiDebug by remember { mutableStateOf(false) }
    var packageName by remember { mutableStateOf(false) }
    var capture by remember { mutableStateOf(false) }
    var root by remember { mutableStateOf(false) }
    var network by remember { mutableStateOf(false) }
    var expiry by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ProtectionToggleRow("签名校验", signature) { signature = it }
        ProtectionToggleRow("反调试", antiDebug) { antiDebug = it }
        ProtectionToggleRow("包名检测", packageName) { packageName = it }
        ProtectionToggleRow("抓包检测", capture) { capture = it }
        ProtectionToggleRow("Root 检测", root) { root = it }
        ProtectionToggleRow("网络检测", network) { network = it }
        ProtectionToggleRow("到期退出", expiry) { expiry = it }
        Button(
            onClick = onProcess,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("开始处理")
        }
    }
}

@Composable
private fun ProtectionToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onChange)
    }
}
