package com.tianmen.ngz.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tianmen.ngz.ProtectViewModel
import com.tianmen.ngz.ProtectionOptions
import com.tianmen.ngz.ProcessStatus
import com.tianmen.ngz.ui.theme.AmberAlert
import com.tianmen.ngz.ui.theme.Crimson
import com.tianmen.ngz.ui.theme.Jade
import com.tianmen.ngz.ui.theme.TextMuted
import com.tianmen.ngz.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private data class GuardOption(
    val key: String,
    val label: String,
    val desc: String,
    val enabledByDefault: Boolean,
)

private val guardOptions = listOf(
    GuardOption("signature", "签名校验", "启动时校验 APK 签名，被重签即退出", true),
    GuardOption("antiDebug", "反调试", "拦截调试器与 TracerPid 附加", true),
    GuardOption("packageName", "包名检测", "校验运行包名未被篡改", true),
    GuardOption("capture", "抓包检测", "检测 VPN 与代理抓包环境", true),
    GuardOption("root", "Root 检测", "检测 su / test-keys 授权环境", true),
    GuardOption("network", "网络检测", "断网即退出", false),
    GuardOption("expiry", "到期退出", "超过设定时间后自动退出", false),
)

@Composable
fun ProcessScreen(viewModel: ProtectViewModel) {
    val selectedApk by viewModel.selectedApk.collectAsState()
    val processState by viewModel.processState.collectAsState()
    val apkPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri -> uri?.let(viewModel::importApk) }

    val toggles = remember {
        mutableStateOf(guardOptions.associate { it.key to it.enabledByDefault })
    }
    var expiryMillis by rememberSaveable { mutableLongStateOf(System.currentTimeMillis() + 30L * 24 * 3600 * 1000) }
    var exitOnHit by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(20.dp))
        ApkPickerCard(
            hasSelection = selectedApk != null,
            fileName = processState.inputName,
            fileSize = processState.inputSizeLabel,
            onClick = { apkPicker.launch(arrayOf("application/vnd.android.package-archive", "application/zip", "*/*")) },
        )
        Spacer(Modifier.height(28.dp))
        SectionHeader("PROTECTION MATRIX", "选择要注入目标 APK 的防护能力")
        Spacer(Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            guardOptions.forEach { option ->
                val checked = toggles.value[option.key] == true
                GuardToggleCard(
                    option = option,
                    checked = checked,
                    onCheckedChange = { toggles.value = toggles.value + (option.key to it) },
                )
            }
        }
        AnimatedVisibility(toggles.value["expiry"] == true) {
            Column {
                Spacer(Modifier.height(10.dp))
                ExpiryCard(expiryMillis = expiryMillis, onPick = { expiryMillis = it }, exitOnHit = exitOnHit, onExitChange = { exitOnHit = it })
            }
        }
        Spacer(Modifier.height(28.dp))
        ProcessButton(
            enabled = selectedApk != null && processState.status != ProcessStatus.RUNNING,
            running = processState.status == ProcessStatus.RUNNING,
            onClick = {
                val options = ProtectionOptions(
                    signature = toggles.value["signature"] == true,
                    antiDebug = toggles.value["antiDebug"] == true,
                    packageName = toggles.value["packageName"] == true,
                    capture = toggles.value["capture"] == true,
                    root = toggles.value["root"] == true,
                    network = toggles.value["network"] == true,
                    expiry = toggles.value["expiry"] == true,
                    expiryEpochMillis = expiryMillis,
                    exitOnHit = exitOnHit,
                )
                val name = selectedApk?.name?.removeSuffix(".apk") ?: "output"
                viewModel.process(options, "${name}_ngz.apk")
            },
        )
        if (processState.status != ProcessStatus.IDLE) {
            Spacer(Modifier.height(16.dp))
            StatusBanner(status = processState.status, message = processState.message, outputUri = processState.lastOutputUri)
        }
        Spacer(Modifier.height(28.dp))
    }
}

@Composable
private fun ApkPickerCard(hasSelection: Boolean, fileName: String, fileSize: String, onClick: () -> Unit) {
    val borderColor by animateColorAsState(
        if (hasSelection) Jade.copy(alpha = 0.55f) else MaterialTheme.colorScheme.outline, label = "border"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                if (hasSelection) Icons.Outlined.InsertDriveFile else Icons.Outlined.AddCircleOutline,
                contentDescription = null,
                tint = Jade,
                modifier = Modifier.size(26.dp),
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                if (hasSelection) fileName else "选择或拖入 APK",
                style = MaterialTheme.typography.titleSmall,
                color = if (hasSelection) MaterialTheme.colorScheme.onSurface else TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                if (hasSelection) fileSize else "点击从本地选取待保护的安装包",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
            )
        }
        Icon(Icons.Outlined.FolderOpen, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Column {
        Text(title, style = MaterialTheme.typography.labelSmall, color = Jade)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextMuted)
    }
}

@Composable
private fun GuardToggleCard(option: GuardOption, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (checked) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface)
            .border(1.dp, if (checked) Jade.copy(alpha = 0.35f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Outlined.Shield,
            contentDescription = null,
            tint = if (checked) Jade else TextMuted,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(option.label, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(2.dp))
            Text(option.desc, style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = Jade,
                checkedBorderColor = Jade,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                uncheckedBorderColor = MaterialTheme.colorScheme.outline,
            ),
        )
    }
}

private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

@Composable
private fun ExpiryCard(expiryMillis: Long, onPick: (Long) -> Unit, exitOnHit: Boolean, onExitChange: (Boolean) -> Unit) {
    var showPicker by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("到期时间", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.height(2.dp))
                Text(dateFormat.format(Date(expiryMillis)), style = MaterialTheme.typography.bodySmall, color = AmberAlert)
            }
            Button(
                onClick = { showPicker = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = Jade),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("修改", style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("命中即退出（关闭则仅记录日志）", style = MaterialTheme.typography.bodySmall, color = TextSecondary, modifier = Modifier.weight(1f))
            Switch(checked = exitOnHit, onCheckedChange = onExitChange)
        }
    }
    if (showPicker) {
        DateTimePickerDialog(
            initial = expiryMillis,
            onDismiss = { showPicker = false },
            onConfirm = { onPick(it); showPicker = false },
        )
    }
}

@Composable
private fun ProcessButton(enabled: Boolean, running: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Jade,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = TextMuted,
        ),
    ) {
        Icon(
            if (running) Icons.Outlined.HourglassEmpty else Icons.Outlined.Bolt,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            if (running) "处理中…" else "开始保护",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun StatusBanner(status: ProcessStatus, message: String, outputUri: String) {
    val color = when (status) {
        ProcessStatus.SUCCESS -> Jade
        ProcessStatus.ERROR -> Crimson
        else -> TextSecondary
    }
    val icon = when (status) {
        ProcessStatus.SUCCESS -> Icons.Outlined.CheckCircle
        ProcessStatus.ERROR -> Icons.Outlined.ErrorOutline
        else -> Icons.Outlined.HourglassEmpty
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(color.copy(alpha = 0.10f))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
            .padding(14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(10.dp))
            Text(message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        }
        if (outputUri.isNotEmpty()) {
            Spacer(Modifier.height(6.dp))
            Text(
                "已输出到 Download/${outputUri.substringAfterLast('/').substringBefore('?')}",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontFamily = FontFamily.Monospace,
            )
        }
    }
}
