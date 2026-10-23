package com.tianmen.ngz.settings

import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsRepositoryTest {

    private class InMemorySettingsStore : SettingsStore {
        private var current: NgzSettings = NgzSettings.DEFAULT
        override fun load(): NgzSettings = current
        override fun save(settings: NgzSettings) {
            current = settings
        }
    }

    @Test
    fun defaultOutputDirIsDownload() {
        val repo = SettingsRepository(InMemorySettingsStore())
        assertEquals("/storage/emulated/0/Download", repo.current().outputDir)
    }

    @Test
    fun persistsAutoSignAndKeystore() {
        val repo = SettingsRepository(InMemorySettingsStore())
        repo.update(repo.current().copy(autoSign = true, keyAlias = "mykey", outputDir = "/custom"))
        assertEquals(true, repo.current().autoSign)
        assertEquals("mykey", repo.current().keyAlias)
        assertEquals("/custom", repo.current().outputDir)
    }
}
