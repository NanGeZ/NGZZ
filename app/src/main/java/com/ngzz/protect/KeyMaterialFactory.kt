package com.ngzz.protect

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.X509Certificate
import java.util.Date

data class SigningMaterial(
    val privateKey: PrivateKey,
    val certificate: X509Certificate,
    val alias: String,
    val password: String
)

object KeyMaterialFactory {

    private val STORE_TYPES = listOf("JKS", "PKCS12")

    fun load(config: SignerConfig): SigningMaterial {
        val file = File(config.keystorePath)
        if (!file.exists()) {
            throw ProtectError.KeystoreFailed(
                IllegalStateException("签名文件不存在: ${file.name}")
            )
        }
        return load(file.inputStream(), config)
    }

    fun load(input: InputStream, config: SignerConfig): SigningMaterial {
        val bytes = input.use { it.readBytes() }
        var lastError: Exception? = null
        for (type in STORE_TYPES) {
            try {
                return loadFrom(bytes, type, config)
            } catch (e: Exception) {
                lastError = e
            }
        }
        throw ProtectError.KeystoreFailed(
            IllegalStateException(
                "无法读取签名文件（请检查密码/别名是否正确）: ${lastError?.message}",
                lastError
            )
        )
    }

    private fun loadFrom(bytes: ByteArray, type: String, config: SignerConfig): SigningMaterial {
        val ks = KeyStore.getInstance(type)
        ks.load(bytes.inputStream(), config.storePassword.toCharArray())
        if (!ks.containsAlias(config.alias)) {
            throw ProtectError.KeystoreFailed(
                IllegalStateException("签名文件中不存在别名: ${config.alias}")
            )
        }
        val key = ks.getKey(config.alias, config.keyPassword.toCharArray()) as? PrivateKey
            ?: throw ProtectError.KeystoreFailed(
                IllegalStateException("别名 ${config.alias} 不是私钥条目")
            )
        val cert = ks.getCertificate(config.alias) as? X509Certificate
            ?: throw ProtectError.KeystoreFailed(
                IllegalStateException("别名 ${config.alias} 缺少证书")
            )
        return SigningMaterial(key, cert, config.alias, config.keyPassword)
    }

    fun store(config: SignerConfig, material: SigningMaterial, type: String = "JKS") {
        val ks = KeyStore.getInstance(type)
        ks.load(null, config.storePassword.toCharArray())
        ks.setKeyEntry(
            config.alias,
            material.privateKey,
            config.keyPassword.toCharArray(),
            arrayOf(material.certificate)
        )
        File(config.keystorePath).outputStream().use {
            ks.store(it, config.storePassword.toCharArray())
        }
    }

    fun generate(config: SignerConfig, type: String = "JKS"): SigningMaterial {
        val kpg = KeyPairGenerator.getInstance("RSA")
        kpg.initialize(2048)
        val pair = kpg.generateKeyPair()
        val cert = SelfSignedCert.generate(pair.private, pair.public, config.dn)
        val material = SigningMaterial(pair.private, cert, config.alias, config.keyPassword)
        store(config, material, type)
        return material
    }
}

object SelfSignedCert {
    fun generate(
        privateKey: PrivateKey,
        publicKey: java.security.PublicKey,
        dn: String
    ): X509Certificate {
        val info = org.bouncycastle.asn1.x500.X500Name(dn)
        val serial = BigInteger.valueOf(System.currentTimeMillis())
        val notBefore = Date(System.currentTimeMillis() - 86400000L)
        val notAfter = Date(System.currentTimeMillis() + 86400000L * 3650)
        val builder = org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder(
            info, serial, notBefore, notAfter, info, publicKey
        )
        val signer = org.bouncycastle.operator.jcajce.JcaContentSignerBuilder("SHA256withRSA")
            .build(privateKey)
        val holder = builder.build(signer)
        return org.bouncycastle.cert.jcajce.JcaX509CertificateConverter().getCertificate(holder)
    }
}
