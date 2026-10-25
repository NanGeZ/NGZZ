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
    private const val SIG_V3_ID = 0xf05368c0.toInt()
    private const val CHUNK_SIZE = 1024 * 1024

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
            val zip = ZipFile(input)
            val entries = zip.entries().toList()
            val manifest = Manifest()
            val main = manifest.mainAttributes
            main[Attributes.Name.MANIFEST_VERSION] = "1.0"
            main[Attributes.Name("Created-By")] = "Nantianmen APK Protector"

            val digests = mutableMapOf<String, String>()
            for (e in entries) {
                if (e.isDirectory || e.name == "META-INF/MANIFEST.MF" ||
                    e.name.endsWith(".SF") || e.name.endsWith(".RSA") ||
                    e.name.endsWith(".DSA") || e.name.endsWith(".EC")
                ) continue
                val bytes = zip.getInputStream(e).readBytes()
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(bytes)
                val b64 = android.util.Base64.encodeToString(digest, android.util.Base64.NO_WRAP)
                digests[e.name] = b64
                val attr = Attributes()
                attr[Attributes.Name("SHA-256-Digest")] = b64
                manifest.entries[e.name] = attr
            }

            val manifestBytes = ByteArrayOutputStream().also { manifest.write(it) }.toByteArray()
            val sfBytes = buildSf(manifestBytes, digests)
            val sigBlock = buildSigBlock(cert, key, manifestBytes, sfBytes, entries, zip)

            val tmpOut = File(output.parentFile, output.name + ".tmp")
            val zos = ZipOutputStream(tmpOut.outputStream().buffered())
            zos.setMethod(ZipOutputStream.DEFLATED)

            val written = mutableSetOf<String>()

            for (e in entries) {
                if (e.name.startsWith("META-INF/") && (e.name.endsWith(".SF") || e.name.endsWith(".RSA") ||
                            e.name.endsWith(".DSA") || e.name.endsWith(".EC") || e.name == "META-INF/MANIFEST.MF")
                ) continue
                if (e.isDirectory) continue
                val bytes = zip.getInputStream(e).readBytes()
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
                written.add(e.name)
            }

            val mfEntry = ZipEntry("META-INF/MANIFEST.MF")
            mfEntry.method = ZipEntry.DEFLATED
            zos.putNextEntry(mfEntry)
            zos.write(manifestBytes)
            zos.closeEntry()

            val sfEntry = ZipEntry("META-INF/CERT.SF")
            sfEntry.method = ZipEntry.DEFLATED
            zos.putNextEntry(sfEntry)
            zos.write(sfBytes)
            zos.closeEntry()

            val rsaEntry = ZipEntry("META-INF/CERT.RSA")
            rsaEntry.method = ZipEntry.DEFLATED
            zos.putNextEntry(rsaEntry)
            zos.write(signPkcs7(cert, key, manifestBytes, sfBytes))
            zos.closeEntry()

            zos.close()
            zip.close()

            appendSigBlock(tmpOut, output, sigBlock)
            tmpOut.delete()

            SignResult(true, "签名完成: ${output.name}")
        } catch (t: Throwable) {
            t.printStackTrace()
            SignResult(false, "签名失败: ${t.message}")
        }
    }

    private fun buildSf(manifestBytes: ByteArray, digests: Map<String, String>): ByteArray {
        val sb = StringBuilder()
        sb.append("Signature-Version: 1.0\r\n")
        sb.append("Created-By: Nantianmen APK Protector\r\n")
        val md = MessageDigest.getInstance("SHA-256")
        val mfDigest = android.util.Base64.encodeToString(md.digest(manifestBytes), android.util.Base64.NO_WRAP)
        sb.append("SHA-256-Digest-Manifest: $mfDigest\r\n\r\n")
        for ((name, digest) in digests) {
            sb.append("Name: $name\r\n")
            sb.append("SHA-256-Digest: $digest\r\n\r\n")
        }
        return sb.toString().toByteArray(Charsets.UTF_8)
    }

    private fun buildSigBlock(
        cert: X509Certificate,
        key: PrivateKey,
        manifestBytes: ByteArray,
        sfBytes: ByteArray,
        entries: List<ZipEntry>,
        zip: ZipFile
    ): ByteArray {
        val contentDigests = computeContentDigests(entries, zip)

        val signerV2 = buildV2Signer(cert, key, contentDigests[0])
        val signerV3 = buildV3Signer(cert, key, contentDigests[0])

        val v2Block = pair(SIG_V2_ID, lengthPrefixedList(signerV2))
        val v3Block = pair(SIG_V3_ID, lengthPrefixedList(signerV3))

        val sizeOfBlock = v2Block.size.toLong() + v3Block.size.toLong() + 16
        val block = ByteArrayOutputStream()
        block.write(le64(sizeOfBlock))
        block.write(v2Block)
        block.write(v3Block)
        block.write(le64(sizeOfBlock))
        block.write(SIG_BLOCK_MAGIC.toByteArray(Charsets.US_ASCII))
        return block.toByteArray()
    }

    private fun computeContentDigests(entries: List<ZipEntry>, zip: ZipFile): List<ByteArray> {
        val chunks = mutableListOf<ByteArray>()
        val sorted = entries.filter { !it.isDirectory }.sortedBy { it.name }
        for (e in sorted) {
            val bytes = zip.getInputStream(e).readBytes()
            chunks.add(le32(if (e.method == ZipEntry.STORED) 0x00 else 0x01))
            chunks.add(le32(bytes.size))
            chunks.add(bytes)
        }
        val md1 = MessageDigest.getInstance("SHA-256")
        val md2 = MessageDigest.getInstance("SHA-512")
        for (chunk in chunks) {
            md1.update(chunk)
            md2.update(chunk)
        }
        return listOf(md1.digest(), md2.digest())
    }

    private fun buildV2Signer(cert: X509Certificate, key: PrivateKey, digest: ByteArray): ByteArray {
        val signedData = DERSequence(
            arrayOf(
                DERSequence(ASN1EncodableVector().apply {
                    add(ASN1Integer(0))
                    add(DERSequence(arrayOf<org.bouncycastle.asn1.ASN1Encodable>(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE)))
                    add(org.bouncycastle.asn1.ASN1OctetString.getInstance(digest))
                }),
                DERSequence(arrayOf(certCertificate(cert)))
            )
        )

        val sigBytes = signData(key, signedData.encoded)
        val signatures = DERSequence(
            arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                DERSequence(
                    arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                        AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption, DERNull.INSTANCE),
                        org.bouncycastle.asn1.DEROctetString(sigBytes)
                    )
                )
            )
        )

        val signedDataFull = DERSequence(
            arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                signedData,
                DERSequence(
                    arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                        AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption, DERNull.INSTANCE),
                        org.bouncycastle.asn1.DEROctetString(sigBytes)
                    )
                ),
                org.bouncycastle.asn1.DEROctetString(cert.encoded)
            )
        )

        return DERSequence(
            arrayOf(
                signedDataFull,
                signatures,
                DERSequence(arrayOf(ASN1Integer(0)))
            )
        ).encoded
    }

    private fun buildV3Signer(cert: X509Certificate, key: PrivateKey, digest: ByteArray): ByteArray {
        return buildV2Signer(cert, key, digest)
    }

    private fun certCertificate(cert: X509Certificate): org.bouncycastle.asn1.ASN1Encodable {
        return org.bouncycastle.asn1.ASN1Primitive.fromByteArray(cert.encoded)
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

        val digestAlgorithms = DERSet(
            DERSequence(
                arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                    AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE)
                )
            )
        )

        val contentInfo = DERSequence(
            arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                PKCSObjectIdentifiers.data,
                org.bouncycastle.asn1.DEROctetString(sfBytes)
            )
        )

        val certificates = org.bouncycastle.asn1.ASN1Set.getInstance(
            org.bouncycastle.asn1.ASN1Primitive.fromByteArray(cert.encoded)
        )

        val signerInfo = DERSequence(
            arrayOf<org.bouncycastle.asn1.ASN1Encodable>(
                ASN1Integer(1),
                DERSequence(arrayOf<org.bouncycastle.asn1.ASN1Encodable>(certIssuer(cert), ASN1Integer(cert.serialNumber))),
                algorithmIdentifier,
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
                signedData
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

    private fun appendSigBlock(tmpIn: File, out: File, sigBlock: ByteArray) {
        val original = tmpIn.readBytes()
        val eocdOffset = findEocd(original)
        val cdOffset = readLe32(original, eocdOffset + 16)
        val cdSize = readLe32(original, eocdOffset + 12)
        val cdOffset64 = cdOffset.toLong() and 0xFFFFFFFFL
        val cdSize64 = cdSize.toLong() and 0xFFFFFFFFL

        val newCdOffset = sigBlock.size.toLong() + cdOffset64
        val beforeCd = original.copyOfRange(0, cdOffset64.toInt())
        val cd = original.copyOfRange(cdOffset64.toInt(), cdOffset64.toInt() + cdSize64.toInt())
        val eocd = original.copyOfRange(eocdOffset, original.size)

        val newEocd = eocd.copyOf()
        writeLe32(newEocd, 16, newCdOffset.toInt())

        val outStream = out.outputStream()
        outStream.write(sigBlock)
        outStream.write(cd)
        outStream.write(newEocd)
        outStream.close()
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

    private fun pair(id: Int, value: ByteArray): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(le64(value.size.toLong() + 4))
        out.write(le32(id))
        out.write(value)
        return out.toByteArray()
    }

    private fun lengthPrefixedList(vararg items: ByteArray): ByteArray {
        val total = items.sumOf { it.size + 4 }
        val out = ByteArrayOutputStream()
        out.write(le32(total))
        for (item in items) {
            out.write(le32(item.size))
            out.write(item)
        }
        return out.toByteArray()
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
