package com.nantianmen.protect.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nantianmen.protect.core.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NantianmenApp(
    state: UiState,
    onPickApk: () -> Unit,
    onToggleFeature: (ProtectFeature) -> Unit,
    onSetAutoSign: (Boolean) -> Unit,
    onStartProtect: () -> Unit,
    onSetTab: (Int) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = state.tab == 0,
                    onClick = { onSetTab(0) },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "首页") },
                    label = { Text("首页") }
                )
                NavigationBarItem(
                    selected = state.tab == 1,
                    onClick = { onSetTab(1) },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "设置") },
                    label = { Text("设置") }
                )
            }
        }
    ) { padding ->
        when (state.tab) {
            0 -> HomeScreen(state, onPickApk, onToggleFeature, onStartProtect, Modifier.padding(padding))
            1 -> SettingsScreen(state, onSetAutoSign, Modifier.padding(padding))
        }
    }
}

@Composable
fun HomeScreen(
    state: UiState,
    onPickApk: () -> Unit,
    onToggleFeature: (ProtectFeature) -> Unit,
    onStartProtect: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0DC))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "南天门",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C2C2C)
            )
            Text(
                "APK 保护处理工具箱",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E0C8)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("选择 APK 文件", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = onPickApk,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A6FA5))
                    ) {
                        Text(if (state.selectedApkName.isEmpty()) "浏览文件" else state.selectedApkName)
                    }
                    state.analysis?.let { a ->
                        Spacer(Modifier.height(8.dp))
                        Text("包名: ${a.manifest?.packageName ?: "未知"}", fontSize = 13.sp)
                        Text("版本: ${a.manifest?.versionName ?: "-"}  |  DEX: ${a.dexCount}", fontSize = 13.sp)
                        Text("原生库: ${a.nativeLibs.abis.joinToString(", ").ifEmpty { "无" }}", fontSize = 13.sp)
                    }
                }
            }
        }

        item {
            Text("保护功能", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF2C2C2C))
        }

        items(ProtectFeature.entries.toList()) { feature ->
            FeatureRow(
                feature = feature,
                checked = feature in state.selectedFeatures,
                onToggle = { onToggleFeature(feature) }
            )
        }

        item {
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onStartProtect,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = state.selectedFeatures.isNotEmpty() && !state.isProtecting && state.selectedApkUri != null,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D7D46)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (state.isProtecting) "处理中..." else "开始保护",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (state.isProtecting) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E0C8))
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(state.progress.message, fontWeight = FontWeight.Bold)
                        Text(state.progress.detail, fontSize = 13.sp)
                        Spacer(Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = state.progress.percent,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        state.result?.let { result ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (result.success) Color(0xFFD4EDDA) else Color(0xFFF8D7DA)
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            if (result.success) "✓ 保护完成" else "✗ 处理失败",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        result.outputApkPath?.let {
                            Text("输出: $it", fontSize = 12.sp)
                        }
                        result.messages.forEach {
                            Text(it, fontSize = 12.sp)
                        }
                        result.verification?.details?.forEach {
                            Text(it, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeatureRow(feature: ProtectFeature, checked: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE8D0)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(feature.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(feature.desc, fontSize = 13.sp, color = Color(0xFF888888))
            }
            Switch(
                checked = checked,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF4A6FA5),
                    checkedTrackColor = Color(0xFFB0C4E0)
                )
            )
        }
    }
}

@Composable
fun SettingsScreen(state: UiState, onSetAutoSign: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0DC))
            .padding(16.dp)
    ) {
        Text("设置", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2C2C2C))
        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE8D0)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("APK 处理后自动签名", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("开启后自动对输出 APK 进行签名", fontSize = 13.sp, color = Color(0xFF888888))
                }
                Switch(
                    checked = state.autoSign,
                    onCheckedChange = { onSetAutoSign(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF4A6FA5),
                        checkedTrackColor = Color(0xFFB0C4E0)
                    )
                )
            }
        }
    }
}
