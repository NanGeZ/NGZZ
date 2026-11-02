package com.seedy.tool.engine

import java.io.File

/**
 * 过签方案（“过签”指绕过目标 APK 的自签名校验，不是签名方案）。
 * 处理输出默认【不签名】，留待用户自行签名。
 */
enum class Plan(
    val code: String,
    val title: String,
    val desc: String,
    val injects: List<String>,
) {
    PMS_HOOK(
        code = "A",
        title = "方案 A · PMS Hook（推荐）",
        desc = "注入原签名记录（_.s）+ hook.dex 运行时载荷。载荷在目标应用进程内" +
            "三层替换 IPackageManager（FakeCache → FakeSM → FakeBinder），" +
            "把自查签名改写为原始证书，免 root。",
        injects = listOf("_.s", "assets/hook.dex", "_.m"),
    ),
    EARLY_INJECT(
        code = "B",
        title = "方案 B · 早注入变体",
        desc = "与方案 A 同载荷，但入口桩为早注入变体（attachBaseContext 阶段入口，" +
            "_.m 标记 B）。适用于 onCreate 自校验执行较早的目标应用，免 root。",
        injects = listOf("_.s", "assets/hook.dex", "_.m"),
    ),
    SIG_RECORD_ONLY(
        code = "C",
        title = "方案 C · 仅注入签名记录",
        desc = "只写入原签名记录（_.s），不注入运行时载荷。用于配合外部重建/加壳环节，" +
            "或只需保留原始签名凭据的场景。",
        injects = listOf("_.s"),
    );

    companion object {
        /** 局限说明：三个方案共同的不支持范围，需在 UI 明示。 */
        val LIMITATIONS = listOf(
            "native 层自校验（JNI 直读 /proc/self/maps、自行解析签名块）不支持",
            "APK 完整性 / 文件哈希自校验不支持（注入会改变文件内容）",
            "Play Integrity / SafetyNet 等服务端校验不支持",
            "输出 APK 未签名，需自行签名后再安装",
        )
    }
}

/** APK 分析结果。 */
data class ApkAnalysis(
    val schemes: List<String>,
    val certs: List<CertInfo>,
    val entryCount: Int,
    val sizeBytes: Long,
) {
    val schemeText: String get() = schemes.joinToString(" / ").ifEmpty { "未检测到签名" }
}

data class CertInfo(
    val der: ByteArray,
    val sha256: String,
    val source: String,
) {
    override fun equals(other: Any?) = other is CertInfo && der.contentEquals(other.der)
    override fun hashCode() = der.contentHashCode()
}

/** 处理结果。 */
sealed class ProcessResult {
    data class Success(
        val plan: Plan,
        val analysis: ApkAnalysis,
        val injected: List<String>,
        val outputName: String,
        /** 已写到所选 APK 同目录时为显示路径，否则 null（走用户选目录流程）。 */
        val savedTo: String?,
        /** savedTo == null 时待落盘的缓存文件。 */
        val pendingFile: File?,
    ) : ProcessResult()

    data class Error(val message: String) : ProcessResult()
}
