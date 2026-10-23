package com.tianmen.ngz.settings

interface SettingsStore {
    fun load(): NgzSettings
    fun save(settings: NgzSettings)
}

class SettingsRepository(private val store: SettingsStore) {
    fun current(): NgzSettings = store.load()
    fun update(settings: NgzSettings) = store.save(settings)
}
