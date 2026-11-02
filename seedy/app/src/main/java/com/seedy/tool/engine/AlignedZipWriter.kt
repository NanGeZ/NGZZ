package com.seedy.tool.engine

import java.io.BufferedOutputStream
import java.io.OutputStream
import java.util.zip.CRC32
import java.util.zip.Deflater

/**
 * 轻量 ZIP 写入器：重写 APK 时保持 STORED 条目不压缩，并保证所有条目数据
 * 起始偏移 4 字节对齐（等价 zipalign -f 4），输出为未签名 APK。
 */
class AlignedZipWriter(out: OutputStream) {

    private data class Rec(
        val name: ByteArray,
        val method: Int,
        val crc: Long,
        val csize: Long,
        val usize: Long,
        val offset: Long,
        val time: Int,
        val date: Int,
    )

    private val buf = BufferedOutputStream(out, 256 * 1024)
    private val records = mutableListOf<Rec>()
    private var offset = 0L
    private var closed = false

    /** 写入一个条目；store=true 时原样存储（并对齐），否则 deflate。 */
    fun put(name: String, data: ByteArray, store: Boolean, timeMs: Long = System.currentTimeMillis()) {
        val nameBytes = name.toByteArray(Charsets.UTF_8)
        val crc = CRC32().apply { update(data) }.value
        val (dosTime, dosDate) = dosDateTime(timeMs)

        val method = if (store) METHOD_STORED else METHOD_DEFLATED
        val payload = if (store) data else deflate(data)
        val headerOffset = offset

        // 数据偏移 = 本地头(30) + name + extra；用 extra 填充到对齐
        // 常规条目 4 字节对齐；lib/*.so 需 16KB 页对齐（zipalign -p 要求）
        val align = if (name.startsWith("lib/") && name.endsWith(".so")) PAGE_ALIGN else 4
        val base = offset + 30 + nameBytes.size
        val padData = ((align - ((base + 4) % align)) % align).toInt()
        // extra 结构：id(2) + size(2) + data；仅在需要填充时写
        val extraLen = if (padData == 0) 0 else padData + 4

        writeInt(0x04034b50)          // local header sig
        writeShort(20)                // version needed
        writeShort(0)                 // flags
        writeShort(method)
        writeShort(dosTime)
        writeShort(dosDate)
        writeInt(crc.toInt())
        writeInt(payload.size)
        writeInt(data.size)
        writeShort(nameBytes.size)
        writeShort(extraLen)
        write(nameBytes)
        if (extraLen != 0) {
            writeShort(EXTRA_ID_PADDING)
            writeShort(padData)
            write(ByteArray(padData))
        }
        write(payload)

        records += Rec(nameBytes, method, crc, payload.size.toLong(), data.size.toLong(), headerOffset, dosTime, dosDate)
    }

    fun finish() {
        if (closed) return
        closed = true
        val cdStart = offset
        for (r in records) {
            writeInt(0x02014b50)   // central dir sig
            writeShort(20)         // version made by
            writeShort(20)         // version needed
            writeShort(0)          // flags
            writeShort(r.method)
            writeShort(r.time)
            writeShort(r.date)
            writeInt(r.crc.toInt())
            writeInt(r.csize.toInt())
            writeInt(r.usize.toInt())
            writeShort(r.name.size)
            writeShort(0)          // extra len
            writeShort(0)          // comment len
            writeShort(0)          // disk start
            writeShort(0)          // internal attrs
            writeInt(0)            // external attrs
            writeInt(r.offset.toInt())
            write(r.name)
        }
        val cdSize = offset - cdStart
        writeInt(0x06054b50)       // EOCD
        writeShort(0); writeShort(0)
        writeShort(records.size); writeShort(records.size)
        writeInt(cdSize.toInt())
        writeInt(cdStart.toInt())
        writeShort(0)
        buf.flush()
    }

    // ---------------------------------------------------------------- io

    private fun write(b: ByteArray) {
        buf.write(b)
        offset += b.size
    }

    private fun writeInt(v: Int) {
        write(byteArrayOf((v).toByte(), (v ushr 8).toByte(), (v ushr 16).toByte(), (v ushr 24).toByte()))
    }

    private fun writeShort(v: Int) {
        write(byteArrayOf((v).toByte(), (v ushr 8).toByte()))
    }

    private fun deflate(data: ByteArray): ByteArray {
        val d = Deflater(Deflater.DEFAULT_COMPRESSION, true)
        d.setInput(data)
        d.finish()
        val out = java.io.ByteArrayOutputStream(data.size / 2 + 64)
        val tmp = ByteArray(64 * 1024)
        while (!d.finished()) {
            val n = d.deflate(tmp)
            out.write(tmp, 0, n)
        }
        d.end()
        return out.toByteArray()
    }

    private fun dosDateTime(timeMs: Long): Pair<Int, Int> {
        val c = java.util.Calendar.getInstance()
        c.timeInMillis = timeMs
        val year = c.get(java.util.Calendar.YEAR).coerceAtLeast(1980)
        val date = ((year - 1980) shl 9) or
            ((c.get(java.util.Calendar.MONTH) + 1) shl 5) or
            c.get(java.util.Calendar.DAY_OF_MONTH)
        val time = (c.get(java.util.Calendar.HOUR_OF_DAY) shl 11) or
            (c.get(java.util.Calendar.MINUTE) shl 5) or
            (c.get(java.util.Calendar.SECOND) / 2)
        return time to date
    }

    companion object {
        private const val METHOD_STORED = 0
        private const val METHOD_DEFLATED = 8
        private const val EXTRA_ID_PADDING = 0xD935
        private const val PAGE_ALIGN = 16384
    }
}
