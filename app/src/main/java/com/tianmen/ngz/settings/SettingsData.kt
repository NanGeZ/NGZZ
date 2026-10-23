package com.tianmen.ngz.settings

data class NgzSettings(
    val autoSign: Boolean,
    val keyAlias: String,
    val keyPassword: String,
    val storePassword: String,
    val keystoreUri: String,
    val keystorePath: String,
    val outputDir: String,
) {
    companion object {
        val DEFAULT = NgzSettings(
            autoSign = false,
            keyAlias = "",
            keyPassword = "",
            storePassword = "",
            keystoreUri = "",
            keystorePath = "",
            outputDir = "/storage/emulated/0/Download",
        )
    }
}
