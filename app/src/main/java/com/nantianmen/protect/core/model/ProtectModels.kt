package com.nantianmen.protect.core.model

enum class ProtectFeature(
    val id: String,
    val title: String,
    val desc: String
) {
    ANTI_DEBUG("anti_debug", "反调试", "检测: frida/lsposed/xposed/gg..."),
    PACKAGE_CHECK("package_check", "包名检测", "检测包名是否被篡改"),
    PACKET_CAPTURE("packet_capture", "抓包检测", "检测到VPN/WIFI代理就退出程序"),
    ROOT_CHECK("root_check", "Root检测", "检测设备是否已Root"),
    NETWORK_CHECK("network_check", "网络检测", "如果断网则退出程序");

    companion object {
        fun fromId(id: String): ProtectFeature? = entries.firstOrNull { it.id == id }
    }
}

data class ProtectOptions(
    val features: Set<ProtectFeature> = emptySet(),
    val autoSign: Boolean = true
)

enum class ProtectStep {
    IDLE, ANALYZE, MANIFEST, DEX, ASSEMBLE, SIGN, VERIFY, DONE, ERROR
}

data class ProtectProgress(
    val step: ProtectStep = ProtectStep.IDLE,
    val message: String = "",
    val detail: String = "",
    val percent: Float = 0f
)

data class ProtectResult(
    val success: Boolean,
    val outputApkPath: String?,
    val messages: List<String>,
    val verification: VerificationReport?
)

data class VerificationReport(
    val zipOk: Boolean,
    val manifestOk: Boolean,
    val dexOk: Boolean,
    val signatureOk: Boolean,
    val details: List<String>
)
