package com.tianmen.ngz.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

data class SettingsFormState(
    val autoSign: Boolean,
    val keyAlias: String,
    val keyPassword: String,
    val storePassword: String,
    val keystoreUri: String,
    val outputDir: String,
)

@Composable
fun SettingsScreen(initial: SettingsFormState, onSave: (SettingsFormState) -> Unit) {
    var autoSign by remember { mutableStateOf(initial.autoSign) }
    var keyAlias by remember { mutableStateOf(initial.keyAlias) }
    var keyPassword by remember { mutableStateOf(initial.keyPassword) }
    var storePassword by remember { mutableStateOf(initial.storePassword) }
    var keystoreUri by remember { mutableStateOf(initial.keystoreUri) }
    var outputDir by remember { mutableStateOf(initial.outputDir) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "自动签名", modifier = Modifier.weight(1f))
            Switch(checked = autoSign, onCheckedChange = { autoSign = it })
        }
        OutlinedTextField(
            value = keystoreUri,
            onValueChange = { keystoreUri = it },
            label = { Text("签名文件 URI") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = keyAlias,
            onValueChange = { keyAlias = it },
            label = { Text("keyAlias") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = keyPassword,
            onValueChange = { keyPassword = it },
            label = { Text("keyPassword") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = storePassword,
            onValueChange = { storePassword = it },
            label = { Text("storePassword") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = outputDir,
            onValueChange = { outputDir = it },
            label = { Text("输出目录") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        Button(
            onClick = {
                onSave(
                    SettingsFormState(
                        autoSign = autoSign,
                        keyAlias = keyAlias,
                        keyPassword = keyPassword,
                        storePassword = storePassword,
                        keystoreUri = keystoreUri,
                        outputDir = outputDir,
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("保存设置")
        }
    }
}
