package com.ngzz.protect

import android.content.Context
import android.net.Uri
import java.io.File

data class AppSettings(
    val autoSign: Boolean = true,
    val useCustomSigner: Boolean = false,
    val customKeystorePath: String = "",
    val customKeystoreName: String = "",
    val alias: String = "nantianmen",
    val storePassword: String = "nantianmen",
    val keyPassword: String = "nantianmen",
    val outputTreeUri: String = "",
    val expiryMillis: Long = 0L
)

class SettingsStore(private val context: Context) {

    private val prefs =
        context.getSharedPreferences("ngzz_settings", Context.MODE_PRIVATE)

    fun load(): AppSettings = AppSettings(
        autoSign = prefs.getBoolean(KEY_AUTO_SIGN, true),
        useCustomSigner = prefs.getBoolean(KEY_USE_CUSTOM_SIGNER, false),
        customKeystorePath = prefs.getString(KEY_CUSTOM_KS_PATH, "") ?: "",
        customKeystoreName = prefs.getString(KEY_CUSTOM_KS_NAME, "") ?: "",
        alias = prefs.getString(KEY_ALIAS, DEFAULT_ALIAS) ?: DEFAULT_ALIAS,
        storePassword = prefs.getString(KEY_STORE_PASS, DEFAULT_PASSWORD) ?: DEFAULT_PASSWORD,
        keyPassword = prefs.getString(KEY_KEY_PASS, DEFAULT_PASSWORD) ?: DEFAULT_PASSWORD,
        outputTreeUri = prefs.getString(KEY_OUTPUT_TREE, "") ?: "",
        expiryMillis = prefs.getLong(KEY_EXPIRY, 0L)
    )

    fun save(settings: AppSettings) {
        prefs.edit()
            .putBoolean(KEY_AUTO_SIGN, settings.autoSign)
            .putBoolean(KEY_USE_CUSTOM_SIGNER, settings.useCustomSigner)
            .putString(KEY_CUSTOM_KS_PATH, settings.customKeystorePath)
            .putString(KEY_CUSTOM_KS_NAME, settings.customKeystoreName)
            .putString(KEY_ALIAS, settings.alias)
            .putString(KEY_STORE_PASS, settings.storePassword)
            .putString(KEY_KEY_PASS, settings.keyPassword)
            .putString(KEY_OUTPUT_TREE, settings.outputTreeUri)
            .putLong(KEY_EXPIRY, settings.expiryMillis)
            .apply()
    }

    fun signerConfig(settings: AppSettings): SignerConfig {
        return if (settings.useCustomSigner && settings.customKeystorePath.isNotEmpty()) {
            SignerConfig(
                keystorePath = settings.customKeystorePath,
                alias = settings.alias,
                storePassword = settings.storePassword,
                keyPassword = settings.keyPassword
            )
        } else {
            SignerConfig(
                keystorePath = builtinKeystore().absolutePath,
                alias = DEFAULT_ALIAS,
                storePassword = DEFAULT_PASSWORD,
                keyPassword = DEFAULT_PASSWORD
            )
        }
    }

    fun builtinKeystore(): File {
        val out = File(context.filesDir, SIGNER_DIR + "/" + BUILTIN_KS_NAME)
        if (out.exists() && out.length() > 0) return out
        out.parentFile?.mkdirs()
        context.assets.open("signer/$BUILTIN_KS_NAME").use { input ->
            out.outputStream().use { input.copyTo(it) }
        }
        return out
    }

    fun importKeystore(uri: Uri): String {
        val name = uri.lastPathSegment?.substringAfterLast('/') ?: "custom.jks"
        val out = File(context.filesDir, "$SIGNER_DIR/custom_${System.currentTimeMillis()}.jks")
        out.parentFile?.mkdirs()
        val input = context.contentResolver.openInputStream(uri)
            ?: throw ProtectError.KeystoreFailed(IllegalStateException("无法读取所选签名文件"))
        input.use { ins ->
            out.outputStream().use { outs -> ins.copyTo(outs) }
        }
        if (out.length() == 0L) {
            throw ProtectError.KeystoreFailed(IllegalStateException("所选签名文件为空"))
        }
        prefs.edit()
            .putBoolean(KEY_USE_CUSTOM_SIGNER, true)
            .putString(KEY_CUSTOM_KS_PATH, out.absolutePath)
            .putString(KEY_CUSTOM_KS_NAME, name)
            .apply()
        return out.absolutePath
    }

    fun resetToBuiltinSigner() {
        prefs.edit()
            .putBoolean(KEY_USE_CUSTOM_SIGNER, false)
            .putString(KEY_ALIAS, DEFAULT_ALIAS)
            .putString(KEY_STORE_PASS, DEFAULT_PASSWORD)
            .putString(KEY_KEY_PASS, DEFAULT_PASSWORD)
            .apply()
    }

    companion object {
        const val BUILTIN_KS_NAME = "nantianmen.jks"
        const val SIGNER_DIR = "signer"
        const val DEFAULT_ALIAS = "nantianmen"
        const val DEFAULT_PASSWORD = "nantianmen"

        private const val KEY_AUTO_SIGN = "auto_sign"
        private const val KEY_USE_CUSTOM_SIGNER = "use_custom_signer"
        private const val KEY_CUSTOM_KS_PATH = "custom_ks_path"
        private const val KEY_CUSTOM_KS_NAME = "custom_ks_name"
        private const val KEY_ALIAS = "signer_alias"
        private const val KEY_STORE_PASS = "signer_store_pass"
        private const val KEY_KEY_PASS = "signer_key_pass"
        private const val KEY_OUTPUT_TREE = "output_tree_uri"
        private const val KEY_EXPIRY = "expiry_millis"
    }
}
