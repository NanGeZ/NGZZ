package com.ngzz.protect

import com.android.apksig.ApkSigner
import java.io.File

data class SignerConfig(
    val keystorePath: String,
    val alias: String,
    val storePassword: String,
    val keyPassword: String = storePassword,
    val dn: String = "CN=NGZZ, OU=Protect, O=NGZZ, C=CN"
)

class ApkSignerWrapper(private val config: SignerConfig) {

    fun sign(inputApk: String, outputApk: String) {
        val material = KeyMaterialFactory.load(config)
        val input = File(inputApk)
        val output = File(outputApk)
        val signerConfig = ApkSigner.SignerConfig.Builder(
            material.alias,
            material.privateKey,
            listOf(material.certificate)
        ).build()
        val signer = ApkSigner.Builder(listOf(signerConfig))
            .setInputApk(input)
            .setOutputApk(output)
            .setV1SigningEnabled(true)
            .setV2SigningEnabled(true)
            .setV3SigningEnabled(true)
            .build()
        signer.sign()
    }
}
