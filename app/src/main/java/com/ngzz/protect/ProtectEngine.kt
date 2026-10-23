package com.ngzz.protect

import java.io.File

class ProtectEngine(
    private val native: NativePack = NativePack()
) {

    fun protect(
        apkPath: String,
        switches: Map<String, Boolean>,
        expiryMillis: Long,
        autoSign: Boolean,
        signerConfig: SignerConfig,
        outputDir: File,
        onProgress: (ProtectProgress) -> Unit = {},
        payload: Map<String, ByteArray>
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
            val inputFile = File(apkPath)
            if (!inputFile.exists() || inputFile.length() == 0L) {
                throw ProtectError.ApkReadFailed()
            }
            try {
                val names = ApkIo.entryNames(apkPath)
                if (!names.contains("AndroidManifest.xml")) {
                    throw ProtectError.ApkParseFailed(
                        IllegalStateException("不是有效的 APK（缺少 AndroidManifest.xml）")
                    )
                }
            } catch (e: ProtectError) {
                throw e
            } catch (e: Exception) {
                throw ProtectError.ApkReadFailed(e)
            }

            if (payload.isEmpty()) {
                throw ProtectError.PayloadExtractFailed(
                    IllegalStateException("保护库为空，无法注入")
                )
            }

            report(ProtectStage.CONFIG)
            val config = try {
                ConfigCodec.build(switches, expiryMillis)
            } catch (e: Exception) {
                throw ProtectError.ConfigBuildFailed(e)
            }

            report(ProtectStage.INJECTING)
            val injectedPath = apkPath + ".ngzz-injected.apk"
            val nativeResult = try {
                native.protect(apkPath, config, injectedPath, payload)
            } catch (e: Exception) {
                throw ProtectError.InjectionFailed(e)
            }
            if (nativeResult != injectedPath) {
                throw ProtectError.InjectionFailed(
                    IllegalStateException(nativeResult)
                )
            }
            val injectedFile = File(injectedPath)
            if (!injectedFile.exists() || injectedFile.length() == 0L) {
                throw ProtectError.InjectionFailed(
                    IllegalStateException("注入产物缺失")
                )
            }

            val finalFile = if (autoSign) {
                report(ProtectStage.SIGNING)
                val signedPath = apkPath + ".ngzz-signed.apk"
                try {
                    ApkSignerWrapper(signerConfig).sign(injectedPath, signedPath)
                } catch (e: ProtectError) {
                    throw e
                } catch (e: Exception) {
                    throw ProtectError.SigningFailed(e)
                }
                val signedFile = File(signedPath)
                if (!signedFile.exists() || signedFile.length() == 0L) {
                    throw ProtectError.SigningFailed(
                        IllegalStateException("签名产物缺失")
                    )
                }
                signedFile
            } else {
                injectedFile
            }

            val apkName = inputFile.name.removeSuffix(".apk")
            val outputName = "$apkName.ngzz.apk"
            outputDir.mkdirs()
            val outputFile = File(outputDir, outputName)
            try {
                finalFile.copyTo(outputFile, overwrite = true)
            } catch (e: Exception) {
                throw ProtectError.OutputWriteFailed(e)
            }

            inputFile.delete()
            injectedFile.delete()
            finalFile.delete()

            report(ProtectStage.DONE, complete = true)
            return outputFile.absolutePath

        } catch (e: ProtectError) {
            throw e
        } catch (e: Exception) {
            throw ProtectError.from(e)
        }
    }
}
