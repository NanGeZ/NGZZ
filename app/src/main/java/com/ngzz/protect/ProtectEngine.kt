package com.ngzz.protect

class ProtectEngine(
    private val native: NativePack = NativePack(),
    private val signer: ApkSignerWrapper = ApkSignerWrapper()
) {

    fun protect(
        apkPath: String,
        switches: Map<String, Boolean>,
        onStage: (String) -> Unit = {},
        payload: Map<String, ByteArray> = emptyMap()
    ): String {
        onStage("读取目标 APK")
        check(apkPath.isNotBlank()) { "未选择 APK" }
        ApkIo.entryNames(apkPath)

        onStage("编译保护配置")
        val config = ConfigCodec.build(switches)

        onStage("解包并注入保护")
        val injectedPath = apkPath + ".ngzz-injected.apk"
        native.protect(apkPath, config, injectedPath, payload)

        onStage("重签名与对齐")
        val signedPath = apkPath + ".ngzz.apk"
        signer.sign(injectedPath, signedPath)

        onStage("完成")
        return signedPath
    }
}
