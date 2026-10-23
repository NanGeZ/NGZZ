package com.ngzz.protect

import java.io.File
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.X509Certificate
import java.math.BigInteger
import java.util.Date

data class SigningMaterial(
    val privateKey: PrivateKey,
    val certificate: X509Certificate,
    val alias: String,
    val password: String
)

object KeyMaterialFactory {

    fun ensure(config: SignerConfig): SigningMaterial {
        val ksFile = File(config.keystorePath)
        if (ksFile.exists()) {
            return load(config)
        }
        return create(config)
    }

    fun load(config: SignerConfig): SigningMaterial {
        val ks = KeyStore.getInstance("JKS")
        File(config.keystorePath).inputStream().use {
            ks.load(it, config.password.toCharArray())
        }
        val key = ks.getKey(config.alias, config.password.toCharArray()) as PrivateKey
        val cert = ks.getCertificate(config.alias) as X509Certificate
        return SigningMaterial(key, cert, config.alias, config.password)
    }

    fun create(config: SignerConfig): SigningMaterial {
        val kpg = KeyPairGenerator.getInstance("RSA")
        kpg.initialize(2048)
        val pair = kpg.generateKeyPair()
        val cert = SelfSignedCert.generate(pair.private, pair.public, config.dn)
        val ks = KeyStore.getInstance("JKS")
        ks.load(null, config.password.toCharArray())
        ks.setKeyEntry(
            config.alias,
            pair.private,
            config.password.toCharArray(),
            arrayOf(cert)
        )
        File(config.keystorePath).outputStream().use {
            ks.store(it, config.password.toCharArray())
        }
        return SigningMaterial(pair.private, cert, config.alias, config.password)
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
