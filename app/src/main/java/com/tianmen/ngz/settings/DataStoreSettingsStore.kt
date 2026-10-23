package com.tianmen.ngz.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.settingsDataStore by preferencesDataStore(name = "ngz_settings")

class DataStoreSettingsStore(private val context: Context) : SettingsStore {

    private object Keys {
        val AUTO_SIGN = booleanPreferencesKey("autoSign")
        val KEY_ALIAS = stringPreferencesKey("keyAlias")
        val KEY_PASSWORD = stringPreferencesKey("keyPassword")
        val STORE_PASSWORD = stringPreferencesKey("storePassword")
        val KEYSTORE_URI = stringPreferencesKey("keystoreUri")
        val KEYSTORE_PATH = stringPreferencesKey("keystorePath")
        val OUTPUT_DIR = stringPreferencesKey("outputDir")
    }

    override fun load(): NgzSettings = runBlocking {
        val prefs = context.settingsDataStore.data.first()
        NgzSettings(
            autoSign = prefs[Keys.AUTO_SIGN] ?: NgzSettings.DEFAULT.autoSign,
            keyAlias = prefs[Keys.KEY_ALIAS] ?: NgzSettings.DEFAULT.keyAlias,
            keyPassword = prefs[Keys.KEY_PASSWORD] ?: NgzSettings.DEFAULT.keyPassword,
            storePassword = prefs[Keys.STORE_PASSWORD] ?: NgzSettings.DEFAULT.storePassword,
            keystoreUri = prefs[Keys.KEYSTORE_URI] ?: NgzSettings.DEFAULT.keystoreUri,
            keystorePath = prefs[Keys.KEYSTORE_PATH] ?: NgzSettings.DEFAULT.keystorePath,
            outputDir = prefs[Keys.OUTPUT_DIR] ?: NgzSettings.DEFAULT.outputDir,
        )
    }

    override fun save(settings: NgzSettings) {
        runBlocking {
            context.settingsDataStore.edit { prefs ->
                prefs[Keys.AUTO_SIGN] = settings.autoSign
                prefs[Keys.KEY_ALIAS] = settings.keyAlias
                prefs[Keys.KEY_PASSWORD] = settings.keyPassword
                prefs[Keys.STORE_PASSWORD] = settings.storePassword
                prefs[Keys.KEYSTORE_URI] = settings.keystoreUri
                prefs[Keys.KEYSTORE_PATH] = settings.keystorePath
                prefs[Keys.OUTPUT_DIR] = settings.outputDir
            }
        }
    }
}
