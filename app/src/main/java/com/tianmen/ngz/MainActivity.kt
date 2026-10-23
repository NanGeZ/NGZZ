package com.tianmen.ngz

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tianmen.ngz.engine.pipeline.ProtectionConfig
import com.tianmen.ngz.engine.pipeline.ProtectionPipeline
import com.tianmen.ngz.settings.SettingsRepository
import com.tianmen.ngz.ui.HomeScreen
import com.tianmen.ngz.ui.SettingsFormState
import com.tianmen.ngz.ui.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NgzApp(settingsRepository)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NgzApp(settingsRepository: SettingsRepository) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("南天门") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "设置")
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(
                    onProcess = {
                        val settings = settingsRepository.current()
                        val input = File("/storage/emulated/0/Download/sample.apk")
                        if (input.exists()) {
                            val output = File(settings.outputDir, "sample-protected.apk")
                            ProtectionPipeline().run(
                                ProtectionConfig.Builder()
                                    .inputApk(input)
                                    .outputApk(output)
                                    .autoSign(settings.autoSign && settings.keystoreUri.isNotEmpty())
                                    .keystore(
                                        if (settings.keystoreUri.isNotEmpty())
                                            File(Uri.parse(settings.keystoreUri).path ?: "")
                                        else null
                                    )
                                    .keyAlias(settings.keyAlias)
                                    .keyPassword(settings.keyPassword)
                                    .storePassword(settings.storePassword)
                                    .injectGuard(true)
                                    .build()
                            )
                        }
                    }
                )
            }
            composable("settings") {
                val initial = settingsRepository.current()
                SettingsScreen(
                    initial = SettingsFormState(
                        autoSign = initial.autoSign,
                        keyAlias = initial.keyAlias,
                        keyPassword = initial.keyPassword,
                        storePassword = initial.storePassword,
                        keystoreUri = initial.keystoreUri,
                        outputDir = initial.outputDir,
                    ),
                    onSave = { form ->
                        settingsRepository.update(
                            initial.copy(
                                autoSign = form.autoSign,
                                keyAlias = form.keyAlias,
                                keyPassword = form.keyPassword,
                                storePassword = form.storePassword,
                                keystoreUri = form.keystoreUri,
                                outputDir = form.outputDir,
                            )
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
