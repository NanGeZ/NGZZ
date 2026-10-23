package com.ngzz.protect

class ProtectEngine(private val native: NativePack = NativePack()) {

    fun protect(apkPath: String, switches: Map<String, Boolean>, onStage: (String) -> Unit): String {
        onStage("读取目标 APK")
        check(apkPath.isNotBlank()) { "未选择 APK" }
        ApkIo.entryNames(apkPath)

        onStage("编译保护配置")
        val config = ConfigCodec.build(switches)

        onStage("解包并注入保护")
        val outPath = native.protect(apkPath, config, apkPath + ".ngzz.apk")
        onStage("重签名与对齐")
        onStage("完成")
        return outPath
    }
}
