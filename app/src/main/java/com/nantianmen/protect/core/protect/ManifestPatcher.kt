package com.nantianmen.protect.core.protect

import com.nantianmen.protect.core.model.ManifestInfo

object ManifestPatcher {

    const val GUARD_APPLICATION = "com.nantianmen.guard.GuardApplication"

    fun resolveOriginalApplication(info: ManifestInfo?, pkg: String): String {
        val appClass = info?.applicationClass ?: ""
        return when {
            appClass.isEmpty() -> "android.app.Application"
            appClass.startsWith(".") -> pkg + appClass
            appClass.contains(".") -> appClass
            else -> "$pkg.$appClass"
        }
    }
}
