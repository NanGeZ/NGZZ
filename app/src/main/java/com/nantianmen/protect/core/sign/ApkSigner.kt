package com.nantianmen.protect.core.sign

import org.bouncycastle.asn1.ASN1EncodableVector
import org.bouncycastle.asn1.ASN1Integer
import org.bouncycastle.asn1.DERNull
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSet
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.cert.X509v3CertificateBuilder
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.SecureRandom
import java.security.Security
import java.security.cert.X509Certificate
import java.util.Collections
import java.util.jar.Attributes
import java.util.jar.Manifest
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

object ApkSigner {

    private const val SIG_BLOCK_MAGIC = "APK Sig Block 42"
    private const val SIG_V2_ID = 0x7109871a
    private const val CHUNK_SIZE = 1024 * 1024
    private const val ALG_RSA_PKCS1_SHA256 = 0x0103

    data class SignResult(val success: Boolean, val message: String)

    fun generateKeyStore(file: File, password: String): SignResult {
        return try {
            Security.addProvider(BouncyCastleProvider())
            val kpg = KeyPairGenerator.getInstance("RSA")
            kpg.initialize(2048, SecureRandom())
            val kp = kpg.generateKeyPair()
            val cert = selfSignedCert(kp.private, kp.public as java.security.PublicKey)
            val ks = KeyStore.getInstance("PKCS12")
            ks.load(null, password.toCharArray())
            ks.setKeyEntry("nantianmen", kp.private, password.toCharArray(), arrayOf(cert))
            file.outputStream().use { ks.store(it, password.toCharArray()) }
            SignResult(true, "签名密钥已生成: ${file.name}")
        } catch (t: Throwable) {
            SignResult(false, "生成签名密钥失败: ${t.message}")
        }
    }

    fun loadKey(file: File, password: String): Pair<PrivateKey, X509Certificate>? {
        return try {
            Security.addProvider(BouncyCastleProvider())
            val ks = KeyStore.getInstance("PKCS12")
            file.inputStream().use { ks.load(it, password.toCharArray()) }
            val alias = ks.aliases().nextElement()
            val key = ks.getKey(alias, password.toCharArray()) as PrivateKey
            val cert = ks.getCertificate(alias) as X509Certificate
            key to cert
        } catch (t: Throwable) {
            null
        }
    }

    fun signApk(input: File, output: File, key: PrivateKey, cert: X509Certificate): SignResult {
        return try {
            val manifestBytes: ByteArray
            val sfBytes: ByteArray
            val entries: List<ZipEntry>
            val zip = ZipFile(input)
            try {
                entries = zip.entries().toList()
                val manifest = Manifest()
                val main = manifest.mainAttributes
                main[Attributes.Name.MANIFEST_VERSION] = "1.0"
                main[Attributes.Name("Created-By")] = "Nantianmen APK Protector"

                val digests = mutableMapOf<String, String>()
                for (e in entries) {
                    if (e.isDirectory || isSignatureEntry(e.name)) continue
                    val bytes = zip.getInputStream(e).readBytes()
                    val md = MessageDigest.getInstance("SHA-256")
                    val b64 = java.util.Base64.getEncoder().encodeToString(md.digest(bytes))
                    digests[e.name] = b64
                    val attr = Attributes()
                    attr[Attributes.Name("SHA-256-Digest")] = b64
                    manifest.entries[e.name] = attr
                }
                manifestBytes = ByteArrayOutputStream().also { manifest.write(it) }.toByteArray()
                sfBytes = buildSf(manifestBytes, digests)
            } finally {
                zip.close()
            }

            // 临时文件名必须区别于 input（protect 流水线的 input 也叫 *.apk.tmp）
            val tmpOut = File(output.parentFile, output.name + ".sign.tmp")
            ZipOutputStream(tmpOut.outputStream().buffered()).use { zos ->
                val zip2 = ZipFile(input)
                try {
                    for (e in zip2.entries()) {
                        if (e.isDirectory || isSignatureEntry(e.name)) continue
                        val bytes = zip2.getInputStream(e).readBytes()
                        val entry = ZipEntry(e.name)
                        if (e.method == ZipEntry.STORED) {
                            entry.method = ZipEntry.STORED
                            entry.size = bytes.size.toLong()
                            entry.crc = crc32(bytes)
                        } else {
                            entry.method = ZipEntry.DEFLATED
                        }
                        zos.putNextEntry(entry)
                        zos.write(bytes)
                        zos.closeEntry()
                    }
                } finally {
                    zip2.close()
                }
                for ((name, bytes) in listOf(
                    "META-INF/MANIFEST.MF" to manifestBytes,
                    "META-INF/CERT.SF" to sfBytes,
                    "META-INF/CERT.RSA" to signPkcs7(cert, key, manifestBytes, sfBytes)
                )) {
                    val entry = ZipEntry(name)
                    entry.method = ZipEntry.DEFLATED
                    zos.putNextEntry(entry)
                    zos.write(bytes)
                    zos.closeEntry()
                }
            }

            // 计算 v2 内容摘要：按规范分三段（ZIP 条目区 / 中央目录 / EOCD）分块
            val unsigned = tmpOut.readBytes()
            val eocdOffset = findEocd(unsigned)
            val cdOffset = readLe32(unsigned, eocdOffset + 16)
            val cdSize = readLe32(unsigned, eocdOffset + 12)
            val section1 = unsigned.copyOfRange(0, cdOffset)
            val section3 = unsigned.copyOfRange(cdOffset, eocdOffset)
            val section4 = unsigned.copyOfRange(eocdOffset, unsigned.size)
            val contentDigest = contentDigestV2(listOf(section1, section3, section4))
            val sigBlock = buildV2SigBlock(cert, key, contentDigest)

            appendSigBlock(unsigned, output, sigBlock)
            tmpOut.delete()

            SignResult(true, "签名完成（v1+v2）: ${output.name}")
        } catch (t: Throwable) {
            t.printStackTrace()
            SignResult(false, "签名失败: ${t.message}")
        }
    }

    private fun isSignatureEntry(name: String): Boolean =
        name.startsWith("META-INF/") && (
                name.endsWith(".SF") || name.endsWith(".RSA") || name.endsWith(".DSA") ||
                        name.endsWith(".EC") || name == "META-INF/MANIFEST.MF" || name == "META-INF/CERT.SF"
                )

    private fun buildSf(manifestBytes: ByteArray, digests: Map<String, String>): ByteArray {
        val sb = StringBuilder()
        sb.append("Signature-Version: 1.0\r\n")
        sb.append("Created-By: Nantianmen APK Protector\r\n")
        val md = MessageDigest.getInstance("SHA-256")
        val mfDigest = java.util.Base64.getEncoder().encodeToString(md.digest(manifestBytes))
        sb.append("SHA-256-Digest-Manifest: $mfDigest\r\n")
        sb.append("X-Android-APK-Signed: 2\r\n\r\n")
        for ((name, digest) in digests) {
            sb.append("Name: $name\r\n")
            sb.append("SHA-256-Digest: $digest\r\n\r\n")
        }
        return sb.toString().toByteArray(Charsets.UTF_8)
    }

    /** v2 内容摘要：三段各按 1MB 分块，块摘要 = SHA256(0xa5 || len_le || data)，顶层 = SHA256(0x5a || 块数_le || 拼接) */
    private fun contentDigestV2(sections: List<ByteArray>): ByteArray {
        val chunkDigests = mutableListOf<ByteArray>()
        val md = MessageDigest.getInstance("SHA-256")
        for (section in sections) {
            var off = 0
            while (off < section.size) {
                val len = minOf(CHUNK_SIZE, section.size - off)
                md.reset()
                md.update(0xa5.toByte())
                md.update(le32(len))
                md.update(section, off, len)
                chunkDigests.add(md.digest())
                off += len
            }
        }
        val top = MessageDigest.getInstance("SHA-256")
        top.update(0x5a.toByte())
        top.update(le32(chunkDigests.size))
        for (d in chunkDigests) top.update(d)
        return top.digest()
    }

    /** 按 APK Signature Scheme v2 规范构建含 v2 块的 APK Signing Block（全部为 uint32 小端 length-prefixed 格式） */
    private fun buildV2SigBlock(cert: X509Certificate, key: PrivateKey, digest: ByteArray): ByteArray {
        // signed data = digests + certificates + additional attributes
        val digestElem = le32(ALG_RSA_PKCS1_SHA256) + le32(digest.size) + digest
        val digestsSeq = le32(digestElem.size) + digestElem
        val digestsBlock = le32(digestsSeq.size) + digestsSeq

        val certDer = cert.encoded
        val certsSeq = le32(certDer.size) + certDer
        val certsBlock = le32(certsSeq.size) + certsSeq

        val attrsBlock = le32(0)

        val signedData = digestsBlock + certsBlock + attrsBlock

        val signature = signData(key, signedData)
        val sigElem = le32(ALG_RSA_PKCS1_SHA256) + le32(signature.size) + signature
        val signaturesSeq = le32(sigElem.size) + sigElem

        val spki = cert.publicKey.encoded

        val signer = le32(signedData.size) + signedData +
                le32(signaturesSeq.size) + signaturesSeq +
                le32(spki.size) + spki
        val signerElem = le32(signer.size) + signer
        val signersSeq = le32(signerElem.size) + signerElem

        val pair = le64(signersSeq.size + 4L) + le32(SIG_V2_ID) + signersSeq
        val sizeOfBlock = pair.size + 8 + 16
        return le64(sizeOfBlock.toLong()) + pair +
                le64(sizeOfBlock.toLong()) +
                SIG_BLOCK_MAGIC.toByteArray(Charsets.US_ASCII)
    }

    private fun signData(key: PrivateKey, data: ByteArray): ByteArray {
        val sig = java.security.Signature.getInstance("SHA256withRSA")
        sig.initSign(key)
        sig.update(data)
        return sig.sign()
    }

    private fun signPkcs7(cert: X509Certificate, key: PrivateKey, manifestBytes: ByteArray, sfBytes: ByteArray): ByteArray {
        val content = ByteArrayOutputStream()
        content.write(sfBytes)

        val signature = java.security.Signature.getInstance("SHA256withRSA")
        signature.initSign(key)
        signature.update(sfBytes)
        val sigBytes = signature.sign()

        val algorithmIdentifier = AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption, DERNull.INSTANCE)
        // SignerInfo.digestAlgorithm 必须是纯摘要 OID（id-sha256），签名算法才是 sha256WithRSAEncryption
        val digestAlgorithmIdentifier = AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE)

        val digestAlgorithms = DERSet(
            AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE)
        )

        // ContentInfo.content 为 [0] EXPLICIT，必须用 context tag 0 包裹 OCTET STRING
        val contentInfo = DERSequence(
            arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                PKCSObjectIdentifiers.data,
                org.bouncycastle.asn1.DERTaggedObject(true, 0, org.bouncycastle.asn1.DEROctetString(sfBytes))
            )
        )

        // certificates 为 [0]（implicit 语义，内容为证书 SEQUENCE 本体）
        val certificates = org.bouncycastle.asn1.DERTaggedObject(
            true, 0, org.bouncycastle.asn1.ASN1Primitive.fromByteArray(cert.encoded)
        )

        val signerInfo = DERSequence(
            arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                ASN1Integer(1),
                DERSequence(arrayOf<org.bouncycastle.asn1.ASN1Encodable>(certIssuer(cert), ASN1Integer(cert.serialNumber))),
                digestAlgorithmIdentifier,
                algorithmIdentifier,
                org.bouncycastle.asn1.DEROctetString(sigBytes)
            )
        )

        val signedData = DERSequence(
            arrayOf(
                ASN1Integer(1),
                digestAlgorithms,
                contentInfo,
                certificates,
                DERSet(signerInfo)
            )
        )

        val contentInfoSigned = DERSequence(
            arrayOf(
                PKCSObjectIdentifiers.signedData,
                org.bouncycastle.asn1.DERTaggedObject(true, 0, signedData)
            )
        )

        return contentInfoSigned.encoded
    }

    private fun certIssuer(cert: X509Certificate): org.bouncycastle.asn1.ASN1Encodable {
        return org.bouncycastle.asn1.x500.X500Name.getInstance(
            org.bouncycastle.asn1.ASN1Primitive.fromByteArray(cert.issuerX500Principal.encoded)
        )
    }

    private fun selfSignedCert(key: PrivateKey, pub: java.security.PublicKey): X509Certificate {
        val now = System.currentTimeMillis()
        val notBefore = java.util.Date(now - 365L * 24 * 60 * 60 * 1000)
        val notAfter = java.util.Date(now + 30L * 365 * 24 * 60 * 60 * 1000)
        val name = X500Name("CN=Nantianmen APK Protector")
        val spki = SubjectPublicKeyInfo.getInstance(pub.encoded)
        val builder = X509v3CertificateBuilder(
            name,
            BigInteger.valueOf(now),
            notBefore,
            notAfter,
            name,
            spki
        )
        val signer = JcaContentSignerBuilder("SHA256withRSA").build(key)
        val holder = builder.build(signer)
        return JcaX509CertificateConverter().getCertificate(holder)
    }

    private fun appendSigBlock(original: ByteArray, out: File, sigBlock: ByteArray) {
        val eocdOffset = findEocd(original)
        val cdOffset = readLe32(original, eocdOffset + 16)
        val cdSize = readLe32(original, eocdOffset + 12)

        val beforeCd = original.copyOfRange(0, cdOffset)
        val cd = original.copyOfRange(cdOffset, cdOffset + cdSize)
        val eocd = original.copyOfRange(eocdOffset, original.size)

        val newEocd = eocd.copyOf()
        writeLe32(newEocd, 16, cdOffset + sigBlock.size)

        out.outputStream().use { w ->
            w.write(beforeCd)
            w.write(sigBlock)
            w.write(cd)
            w.write(newEocd)
        }
    }

    private fun findEocd(data: ByteArray): Int {
        val sig = byteArrayOf(0x50, 0x4B, 0x05, 0x06)
        for (i in data.size - 22 downTo 0) {
            if (data[i] == sig[0] && data[i + 1] == sig[1] && data[i + 2] == sig[2] && data[i + 3] == sig[3]) {
                return i
            }
        }
        throw IllegalStateException("EOCD not found")
    }

    private fun le32(v: Int): ByteArray = byteArrayOf(
        (v and 0xFF).toByte(),
        ((v shr 8) and 0xFF).toByte(),
        ((v shr 16) and 0xFF).toByte(),
        ((v shr 24) and 0xFF).toByte()
    )

    private fun le64(v: Long): ByteArray = byteArrayOf(
        (v and 0xFF).toByte(),
        ((v shr 8) and 0xFF).toByte(),
        ((v shr 16) and 0xFF).toByte(),
        ((v shr 24) and 0xFF).toByte(),
        ((v shr 32) and 0xFF).toByte(),
        ((v shr 40) and 0xFF).toByte(),
        ((v shr 48) and 0xFF).toByte(),
        ((v shr 56) and 0xFF).toByte()
    )

    private fun readLe32(data: ByteArray, off: Int): Int =
        (data[off].toInt() and 0xFF) or
                ((data[off + 1].toInt() and 0xFF) shl 8) or
                ((data[off + 2].toInt() and 0xFF) shl 16) or
                ((data[off + 3].toInt() and 0xFF) shl 24)

    private fun writeLe32(data: ByteArray, off: Int, v: Int) {
        data[off] = (v and 0xFF).toByte()
        data[off + 1] = ((v shr 8) and 0xFF).toByte()
        data[off + 2] = ((v shr 16) and 0xFF).toByte()
        data[off + 3] = ((v shr 24) and 0xFF).toByte()
    }

    private fun crc32(data: ByteArray): Long {
        val crc = java.util.zip.CRC32()
        crc.update(data)
        return crc.value
    }
}
