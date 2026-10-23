package com.tianmen.ngz.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.GppGood
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tianmen.ngz.ProtectViewModel
import com.tianmen.ngz.settings.NgzSettings
import com.tianmen.ngz.ui.theme.Jade
import com.tianmen.ngz.ui.theme.TextMuted
import com.tianmen.ngz.ui.theme.TextSecondary

@Composable
fun SettingsScreen(viewModel: ProtectViewModel) {
    val settings by viewModel.settings.collectAsState()
    val message by viewModel.settingsMessage.collectAsState()
    var alias by remember { mutableStateOf(settings.keyAlias) }
    var keyPassword by remember { mutableStateOf(settings.keyPassword) }
    var storePassword by remember { mutableStateOf(settings.storePassword) }
    var outputDir by remember { mutableStateOf(settings.outputDir) }
    var autoSign by remember { mutableStateOf(settings.autoSign) }
    var showKeyPassword by remember { mutableStateOf(false) }
    var showStorePassword by remember { mutableStateOf(false) }

    val keystorePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            viewModel.importKeystore(uri, alias, keyPassword, storePassword)
        }
    }

    LaunchedEffect(settings) {
        alias = settings.keyAlias
        keyPassword = settings.keyPassword
        storePassword = settings.storePassword
        outputDir = settings.outputDir
        autoSign = settings.autoSign
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(20.dp))
        Text("SETTINGS", style = MaterialTheme.typography.labelSmall, color = Jade)
        Spacer(Modifier.height(4.dp))
        Text("签名与输出", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text("自动签名", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.height(2.dp))
                Text("处理完成后用导入的签名自动重签 APK", style = MaterialTheme.typography.bodySmall, color = TextMuted)
            }
            Switch(
                checked = autoSign,
                onCheckedChange = { autoSign = it },
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

        Spacer(Modifier.height(24.dp))
        Text("自定义签名", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(3.dp))
        Text("导入 .jks / .pkcs12 签名文件，需提供别名与密码", style = MaterialTheme.typography.bodySmall, color = TextMuted)
        Spacer(Modifier.height(12.dp))

        val imported = settings.keystorePath.isNotEmpty()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(if (imported) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, if (imported) Jade.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline, RoundedCornerShape(14.dp))
                .clickable { keystorePicker.launch(arrayOf("*/*")) }
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                if (imported) Icons.Outlined.GppGood else Icons.Outlined.Description,
                contentDescription = null,
                tint = if (imported) Jade else TextMuted,
                modifier = Modifier.size(22.dp),
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    if (imported) settings.keystorePath.substringAfterLast('/') else "点击选择签名文件 (.jks)",
                    style = MaterialTheme.typography.titleSmall,
                    color = if (imported) MaterialTheme.colorScheme.onSurface else TextSecondary,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    if (imported) "别名: ${settings.keyAlias}" else "支持 JKS / PKCS12 格式",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                )
            }
        }

        Spacer(Modifier.height(14.dp))
        NgzField(value = alias, onValueChange = { alias = it }, label = "别名 (alias)", placeholder = "例如 nantianmen")
        Spacer(Modifier.height(12.dp))
        NgzField(
            value = keyPassword,
            onValueChange = { keyPassword = it },
            label = "别名密码 (key password)",
            password = true,
            visible = showKeyPassword,
            onToggleVisible = { showKeyPassword = !showKeyPassword },
        )
        Spacer(Modifier.height(12.dp))
        NgzField(
            value = storePassword,
            onValueChange = { storePassword = it },
            label = "签名密码 (store password)",
            password = true,
            visible = showStorePassword,
            onToggleVisible = { showStorePassword = !showStorePassword },
        )
        Spacer(Modifier.height(12.dp))
        NgzField(value = outputDir, onValueChange = { outputDir = it }, label = "输出目录", placeholder = "/storage/emulated/0/Download")

        if (message.isNotEmpty()) {
            Spacer(Modifier.height(14.dp))
            Text(message, style = MaterialTheme.typography.bodySmall, color = Jade)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.updateSettings(
                    NgzSettings(
                        autoSign = autoSign,
                        keyAlias = alias,
                        keyPassword = keyPassword,
                        storePassword = storePassword,
                        keystoreUri = settings.keystoreUri,
                        keystorePath = settings.keystorePath,
                        outputDir = outputDir.trim().ifEmpty { NgzSettings.DEFAULT.outputDir },
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Jade, contentColor = MaterialTheme.colorScheme.onPrimary),
        ) {
            Icon(Icons.Outlined.Save, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("保存设置", style = MaterialTheme.typography.labelLarge)
        }
        Spacer(Modifier.height(28.dp))
    }
}

@Composable
private fun NgzField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    password: Boolean = false,
    visible: Boolean = true,
    onToggleVisible: (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.bodySmall) },
        placeholder = { Text(placeholder, style = MaterialTheme.typography.bodySmall, color = TextMuted) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (password && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (password) KeyboardType.Password else KeyboardType.Text),
        trailingIcon = if (password && onToggleVisible != null) {
            {
                IconButton(onClick = onToggleVisible) {
                    Icon(
                        if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = null,
                        tint = TextMuted,
                    )
                }
            }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Jade,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = Jade,
            unfocusedLabelColor = TextMuted,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = Jade,
        ),
    )
}
