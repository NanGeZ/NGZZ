package com.tianmen.ngz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tianmen.ngz.ui.HistoryScreen
import com.tianmen.ngz.ui.ProcessScreen
import com.tianmen.ngz.ui.SettingsScreen
import com.tianmen.ngz.ui.theme.InkBlack
import com.tianmen.ngz.ui.theme.InkSurface
import com.tianmen.ngz.ui.theme.Jade
import com.tianmen.ngz.ui.theme.NgzTheme
import com.tianmen.ngz.ui.theme.TextMuted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NgzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NgzApp()
                }
            }
        }
    }
}

private enum class NgzTab(val label: String, val icon: ImageVector) {
    PROCESS("保护", Icons.Outlined.Security),
    HISTORY("记录", Icons.Outlined.History),
    SETTINGS("设置", Icons.Outlined.Settings),
}

@Composable
fun NgzApp(viewModel: ProtectViewModel = hiltViewModel()) {
    var tab by rememberSaveable { mutableStateOf(NgzTab.PROCESS.name) }
    val current = NgzTab.valueOf(tab)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        NgzTopBar()
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            when (current) {
                NgzTab.PROCESS -> ProcessScreen(viewModel)
                NgzTab.HISTORY -> HistoryScreen(viewModel)
                NgzTab.SETTINGS -> SettingsScreen(viewModel)
            }
        }
        NgzBottomBar(current = current, onSelect = { tab = it.name })
    }
}

@Composable
private fun NgzTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Jade),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Outlined.VerifiedUser,
                contentDescription = null,
                tint = InkBlack,
                modifier = Modifier.size(20.dp),
            )
        }
        Spacer(Modifier.width(10.dp))
        Column {
            Text("南天门", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
            Text("APK PROTECTION TOOLKIT", style = MaterialTheme.typography.labelSmall, color = TextMuted)
        }
    }
}

@Composable
private fun NgzBottomBar(current: NgzTab, onSelect: (NgzTab) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .background(InkSurface)
            .padding(horizontal = 12.dp, vertical = 10.dp),
    ) {
        NgzTab.entries.forEach { tab ->
            val selected = tab == current
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (selected) Jade.copy(alpha = 0.12f) else androidx.compose.ui.graphics.Color.Transparent)
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    tab.icon,
                    contentDescription = tab.label,
                    tint = if (selected) Jade else TextMuted,
                    modifier = Modifier.size(22.dp),
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    tab.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (selected) Jade else TextMuted,
                )
            }
        }
    }
}
