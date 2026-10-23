package com.ngzz.protect

sealed class ProtectError(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    class ApkNotFound : ProtectError("未选择目标 APK")
    class ApkReadFailed(cause: Throwable? = null) : ProtectError("无法读取 APK 文件", cause)
    class ApkParseFailed(cause: Throwable? = null) : ProtectError("APK 解析失败", cause)
    class ConfigBuildFailed(cause: Throwable? = null) : ProtectError("保护配置编译失败", cause)
    class InjectionFailed(cause: Throwable? = null) : ProtectError("保护注入失败", cause)
    class SigningFailed(cause: Throwable? = null) : ProtectError("签名失败", cause)
    class KeystoreFailed(cause: Throwable? = null) : ProtectError("密钥库操作失败", cause)
    class OutputWriteFailed(cause: Throwable? = null) : ProtectError("无法写出结果文件", cause)
    class PayloadExtractFailed(cause: Throwable? = null) : ProtectError("保护库提取失败", cause)

    companion object {
        fun from(throwable: Throwable): ProtectError {
            return when (throwable) {
                is ProtectError -> throwable
                is java.io.FileNotFoundException -> ApkReadFailed(throwable)
                is java.util.zip.ZipException -> ApkParseFailed(throwable)
                is java.io.IOException -> OutputWriteFailed(throwable)
                is java.security.GeneralSecurityException -> SigningFailed(throwable)
                else -> InjectionFailed(throwable)
            }
        }
    }
}

data class ProtectProgress(
    val stage: String,
    val step: Int,
    val totalSteps: Int,
    val isComplete: Boolean = false
) {
    val percentage: Int
        get() = if (totalSteps == 0) 0 else (step * 100 / totalSteps)
}

enum class ProtectStage(val title: String) {
    READING("读取目标 APK"),
    CONFIG("编译保护配置"),
    INJECTING("解包并注入保护"),
    SIGNING("重签名与对齐"),
    DONE("完成");

    companion object {
        val all = entries.toList()
        fun fromTitle(title: String): ProtectStage? = entries.find { it.title == title }
    }
}

data class ProtectRecord(
    val apkName: String,
    val outputPath: String,
    val success: Boolean,
    val errorMessage: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
