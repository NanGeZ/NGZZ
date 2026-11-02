package com.seedy.tool

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.seedy.tool.ui.AboutScreen
import com.seedy.tool.ui.DisclaimerDialog
import com.seedy.tool.ui.HomeScreen
import com.seedy.tool.ui.theme.SeedyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeedyTheme {
                SeedyApp()
            }
        }
    }
}

private enum class Tab(val label: String, val icon: ImageVector) {
    HOME("首页", Icons.Outlined.Home),
    ABOUT("关于", Icons.Outlined.Info),
}

private const val PREFS = "seedy_prefs"
private const val KEY_DISCLAIMER = "disclaimer_agreed"

@Composable
private fun SeedyApp() {
    val context = LocalContext.current
    var disclaimerAgreed by remember {
        mutableStateOf(context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_DISCLAIMER, false))
    }
    var showDisclaimer by remember { mutableStateOf(false) }
    var tab by remember { mutableStateOf(Tab.HOME) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { t ->
                    NavigationBarItem(
                        selected = tab == t,
                        onClick = { tab = t },
                        icon = { Icon(t.icon, contentDescription = t.label) },
                        label = { Text(t.label) },
                    )
                }
            }
        },
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (tab) {
                Tab.HOME -> HomeScreen()
                Tab.ABOUT -> AboutScreen(onShowDisclaimer = { showDisclaimer = true })
            }
        }
    }

    // 首次启动完整免责声明：必须同意才能使用
    if (!disclaimerAgreed || showDisclaimer) {
        DisclaimerDialog(
            requireAgree = !disclaimerAgreed,
            onAgree = {
                context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                    .edit().putBoolean(KEY_DISCLAIMER, true).apply()
                disclaimerAgreed = true
                showDisclaimer = false
            },
            onDismiss = { showDisclaimer = false },
        )
    }
}
