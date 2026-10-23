package com.ngzz.protect

import com.android.apksig.ApkSigner
import java.io.File
import java.security.PrivateKey
import java.security.cert.X509Certificate

data class SignerConfig(
    val keystorePath: String,
    val alias: String,
    val password: String,
    val dn: String = "CN=NGZZ, OU=Protect, O=NGZZ, C=CN"
) {
    companion object {
        fun default() = SignerConfig(
            keystorePath = "ngzz-signer.jks",
            alias = "ngzz",
            password = "ngzz-protect-2026"
        )
    }
}

class ApkSignerWrapper(private val config: SignerConfig = SignerConfig.default()) {

    fun sign(inputApk: String, outputApk: String) {
        val material = KeyMaterialFactory.ensure(config)
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
