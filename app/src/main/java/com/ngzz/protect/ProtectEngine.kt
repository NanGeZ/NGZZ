package com.ngzz.protect

class ProtectEngine(
    private val native: NativePack = NativePack(),
    private val signer: ApkSignerWrapper = ApkSignerWrapper()
) {

    fun protect(
        apkPath: String,
        switches: Map<String, Boolean>,
        onProgress: (ProtectProgress) -> Unit = {},
        payload: Map<String, ByteArray> = emptyMap()
    ): String {
        val stages = ProtectStage.all
        val totalSteps = stages.size

        fun report(stage: ProtectStage, complete: Boolean = false) {
            val step = stages.indexOf(stage) + 1
            onProgress(ProtectProgress(stage.title, step, totalSteps, complete))
        }

        try {
            report(ProtectStage.READING)
            if (apkPath.isBlank()) throw ProtectError.ApkNotFound()
            try {
                ApkIo.entryNames(apkPath)
            } catch (e: Exception) {
                throw ProtectError.ApkReadFailed(e)
            }

            report(ProtectStage.CONFIG)
            val config = try {
                ConfigCodec.build(switches)
            } catch (e: Exception) {
                throw ProtectError.ConfigBuildFailed(e)
            }

            report(ProtectStage.INJECTING)
            val injectedPath = apkPath + ".ngzz-injected.apk"
            try {
                native.protect(apkPath, config, injectedPath, payload)
            } catch (e: Exception) {
                throw ProtectError.InjectionFailed(e)
            }

            report(ProtectStage.SIGNING)
            val signedPath = apkPath + ".ngzz.apk"
            try {
                signer.sign(injectedPath, signedPath)
            } catch (e: Exception) {
                throw ProtectError.SigningFailed(e)
            }

            report(ProtectStage.DONE, complete = true)
            return signedPath

        } catch (e: ProtectError) {
            throw e
        } catch (e: Exception) {
            throw ProtectError.from(e)
        }
    }
}
