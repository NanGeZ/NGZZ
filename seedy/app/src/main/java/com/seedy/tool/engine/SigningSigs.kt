package com.seedy.tool.engine

import java.io.ByteArrayInputStream
import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.cert.CertificateFactory
import java.util.zip.ZipFile

/**
 * 提取 APK 的“原始签名”证书，供写入 `_.s`（与运行时载荷读取格式一致：
 * 1 字节 count + 每条 int len + cert bytes）。
 *
 * 覆盖三种签名方案：
 *  - v1（JAR）：META-INF 目录下 .RSA/.DSA/.EC 签名块文件（PKCS#7）
 *  - v2 / v3 / v3.1：APK Signing Block（zip 中央目录之前的魔数块）
 */
object SigningSigs {

    // APK Sig Block 42 魔数（footer 后 16 字节）："APK Sig " + "Block 42"
    private const val APK_SIG_BLOCK_MAGIC_LO = 0x20676953204b5041L // "APK Sig " LE
    private const val APK_SIG_BLOCK_MAGIC_HI = 0x3234206b636f6c42L // "Block 42" LE

    private val SIG_V2_ID = 0x7109871a
    private val SIG_V3_ID = 0xf05368c0.toInt() // 超出 Int 字面量范围，显式转换
    private val SIG_V31_ID = 0x1b93ad61

    private val V1_BLOCK = Regex("META-INF/.+\\.(RSA|DSA|EC)$", RegexOption.IGNORE_CASE)

    /** 返回去重后的证书 DER 列表（先 v2/v3 后 v1）。 */
    fun extract(apk: File): List<ByteArray> {
        val out = LinkedHashMap<String, ByteArray>()
        parseSigningBlock(apk).forEach { out.putIfAbsent(it.toHexKey(), it) }
        extractV1(apk).forEach { out.putIfAbsent(it.toHexKey(), it) }
        return out.values.toList()
    }

    /** 检测到的签名方案标签。 */
    fun detectSchemes(apk: File): List<String> {
        val schemes = mutableListOf<String>()
        runCatching {
            val ids = parseSigningBlockIds(apk)
            if (ids.contains(SIG_V31_ID)) schemes += "v3.1"
            if (ids.contains(SIG_V3_ID)) schemes += "v3"
            if (ids.contains(SIG_V2_ID)) schemes += "v2"
        }
        runCatching {
            ZipFile(apk).use { z ->
                if (z.entries().asSequence().any { V1_BLOCK.matches(it.name) }) schemes += "v1"
            }
        }
        return schemes
    }

    // ------------------------------------------------------------------ v1

    private fun extractV1(apk: File): List<ByteArray> {
        val cf = CertificateFactory.getInstance("X.509")
        val out = mutableListOf<ByteArray>()
        runCatching {
            ZipFile(apk).use { z ->
                z.entries().asSequence()
                    .filter { V1_BLOCK.matches(it.name) }
                    .forEach { e ->
                        runCatching {
                            z.getInputStream(e).use { ins ->
                                // generateCertificates 可解析 PKCS#7 SignedData 签名块
                                cf.generateCertificates(ins).forEach { out += it.encoded }
                            }
                        }
                    }
            }
        }
        return out
    }

    // ------------------------------------------------------ v2 / v3 block

    private fun parseSigningBlockIds(apk: File): Set<Int> {
        val block = readSigningBlock(apk) ?: return emptySet()
        val ids = mutableSetOf<Int>()
        forEachPair(block) { id, _ -> ids += id }
        return ids
    }

    private fun parseSigningBlock(apk: File): List<ByteArray> {
        val block = readSigningBlock(apk) ?: return emptyList()
        val out = mutableListOf<ByteArray>()
        forEachPair(block) { id, value ->
            if (id == SIG_V2_ID || id == SIG_V3_ID || id == SIG_V31_ID) {
                out += parseSignerCerts(value)
            }
        }
        return out
    }

    /** 读出 APK Signing Block 的 pairs 区（去掉头尾 size/magic）。 */
    private fun readSigningBlock(apk: File): ByteBuffer? {
        RandomAccessFile(apk, "r").use { raf ->
            val len = raf.length()
            if (len < 22 + 32) return null
            val tailLen = minOf(len, 22 + 65535).toInt()
            val tail = ByteArray(tailLen)
            raf.seek(len - tailLen)
            raf.readFully(tail)

            // 从尾部找 EOCD
            var eocd = -1
            var i = tail.size - 22
            while (i >= 0) {
                if (tail[i] == 0x50.toByte() && tail[i + 1] == 0x4b.toByte() &&
                    tail[i + 2] == 0x05.toByte() && tail[i + 3] == 0x06.toByte()
                ) {
                    val commentLen = u16(tail, i + 20)
                    if (i + 22 + commentLen == tail.size) {
                        eocd = i
                        break
                    }
                }
                i--
            }
            if (eocd < 0) return null

            val cdOffset = u32(tail, eocd + 16).toLong()
            if (cdOffset < 32 || cdOffset > len) return null

            // 签名块 footer：…[pairs][size(8)][magic(16)] | 中央目录
            val footer = ByteArray(24)
            raf.seek(cdOffset - 24)
            raf.readFully(footer)
            if (u64(footer, 8) != APK_SIG_BLOCK_MAGIC_LO || u64(footer, 16) != APK_SIG_BLOCK_MAGIC_HI) return null

            val blockSize = u64(footer, 0)
            val blockStart = cdOffset - blockSize - 8
            if (blockStart < 0 || blockStart > cdOffset - 24) return null

            val pairsLen = (cdOffset - 24 - (blockStart + 8)).toInt()
            if (pairsLen < 0) return null
            val pairs = ByteArray(pairsLen)
            raf.seek(blockStart + 8)
            raf.readFully(pairs)
            return ByteBuffer.wrap(pairs).order(ByteOrder.LITTLE_ENDIAN)
        }
    }

    private inline fun forEachPair(buf: ByteBuffer, action: (Int, ByteArray) -> Unit) {
        runCatching {
            while (buf.remaining() >= 12) {
                val len = buf.long
                if (len < 4 || len > buf.remaining() + 8) break
                val id = buf.int
                val valueLen = (len - 4).toInt()
                val value = ByteArray(valueLen)
                buf.get(value)
                action(id, value)
            }
        }
    }

    /**
     * value 布局（v2/v3 相同）：
     *   u32 signersLen; 重复 { u32 signerLen; signer }
     *   signer: u32 signedDataLen; signedData; …
     *   signedData: u32 digestsLen; digests; u32 certsLen; 重复 { u32 certLen; DER }
     */
    private fun parseSignerCerts(value: ByteArray): List<ByteArray> {
        val out = mutableListOf<ByteArray>()
        runCatching {
            val bb = ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN)
            val signersLen = bb.int
            if (signersLen <= 0 || signersLen > bb.remaining()) return out
            val signersEnd = bb.position() + signersLen
            while (bb.position() < signersEnd) {
                val signerLen = bb.int
                if (signerLen <= 0 || signerLen > bb.remaining()) break
                val signerEnd = bb.position() + signerLen

                val signedDataLen = bb.int
                if (signedDataLen <= 0 || signedDataLen > bb.remaining()) break
                val sdEnd = bb.position() + signedDataLen

                val digestsLen = bb.int
                if (digestsLen < 0 || digestsLen > bb.remaining()) break
                bb.position(bb.position() + digestsLen)

                val certsLen = bb.int
                if (certsLen < 0 || certsLen > bb.remaining()) break
                val certsEnd = bb.position() + certsLen
                while (bb.position() < certsEnd) {
                    val certLen = bb.int
                    if (certLen <= 0 || certLen > bb.remaining()) break
                    val der = ByteArray(certLen)
                    bb.get(der)
                    out += der
                }
                bb.position(sdEnd)
                bb.position(signerEnd)
            }
        }
        return out
    }

    // ---------------------------------------------------------------- util

    private fun u16(b: ByteArray, off: Int) =
        (b[off].toInt() and 0xff) or ((b[off + 1].toInt() and 0xff) shl 8)

    private fun u32(b: ByteArray, off: Int) =
        (b[off].toInt() and 0xff) or
            ((b[off + 1].toInt() and 0xff) shl 8) or
            ((b[off + 2].toInt() and 0xff) shl 16) or
            ((b[off + 3].toInt() and 0xff) shl 24)

    private fun u64(b: ByteArray, off: Int): Long {
        var v = 0L
        for (k in 7 downTo 0) v = (v shl 8) or (b[off + k].toLong() and 0xff)
        return v
    }

    private fun ByteArray.toHexKey(): String =
        joinToString("") { "%02x".format(it) }

    /** 序列化为 `_.s` 条目内容：1 字节 count + 每条 int len + cert bytes。 */
    fun serializeSigEntry(certs: List<ByteArray>): ByteArray {
        val count = minOf(certs.size, 255)
        val out = java.io.ByteArrayOutputStream()
        out.write(count)
        repeat(count) { i ->
            val c = certs[i]
            out.write(c.size ushr 24); out.write(c.size ushr 16)
            out.write(c.size ushr 8); out.write(c.size)
            out.write(c)
        }
        return out.toByteArray()
    }

    /** 证书 SHA-256 指纹（冒号分隔大写十六进制）。 */
    fun sha256Fingerprint(der: ByteArray): String {
        val d = java.security.MessageDigest.getInstance("SHA-256").digest(der)
        return d.joinToString(":") { "%02X".format(it) }
    }
}
