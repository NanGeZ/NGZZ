package com.nantianmen.protect

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.nantianmen.protect.ui.MainViewModel
import com.nantianmen.protect.ui.NantianmenApp

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val pickApk = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let { viewModel.selectApk(it) }
    }

    private val requestWritePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            viewModel.refreshStorage()
        }

    private val requestAllFilesAccess =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.refreshStorage()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val state by viewModel.state.collectAsState()

                    NantianmenApp(
                        state = state,
                        onPickApk = { pickApk.launch(arrayOf("*/*")) },
                        onToggleFeature = { viewModel.toggleFeature(it) },
                        onSetAutoSign = { viewModel.setAutoSign(it) },
                        onStartProtect = { viewModel.startProtect() },
                        onSetTab = { viewModel.setTab(it) },
                        onRequestStorage = { requestStorageAccess() }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 从系统授权页返回后刷新输出目录状态
        viewModel.refreshStorage()
    }

    /** 申请写入 /storage/emulated/0/Nantianmen 所需的存储权限 */
    private fun requestStorageAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val direct = Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.parse("package:$packageName")
            )
            val intent = if (direct.resolveActivity(packageManager) != null) direct
            else Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            requestAllFilesAccess.launch(intent)
        } else {
            requestWritePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}
