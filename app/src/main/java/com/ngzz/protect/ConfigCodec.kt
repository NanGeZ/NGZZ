package com.ngzz.protect

object ConfigCodec {

    private val MAGIC = byteArrayOf(0x4E, 0x5A, 0x43, 0x46)
    const val SIZE = 16

    fun build(switches: Map<String, Boolean>, expiryMillis: Long = 0L): ByteArray {
        val flags = switches.entries.fold(0) { acc, e ->
            val bit = Protection.entries.indexOfFirst { it.id == e.key }
            if (e.value && bit >= 0) acc or (1 shl bit) else acc
        }
        val expirySec = if (expiryMillis > 0) expiryMillis / 1000 else 0L
        val out = ByteArray(SIZE)
        MAGIC.copyInto(out, 0)
        for (i in 0..3) out[4 + i] = ((flags ushr (8 * i)) and 0xff).toByte()
        for (i in 0..7) out[8 + i] = ((expirySec ushr (8 * i)) and 0xff).toByte()
        return out
    }

    fun decode(config: ByteArray): Int {
        val base = if (config.size >= SIZE) 4 else 0
        return (config[base].toInt() and 0xff) or
            ((config[base + 1].toInt() and 0xff) shl 8) or
            ((config[base + 2].toInt() and 0xff) shl 16) or
            ((config[base + 3].toInt() and 0xff) shl 24)
    }

    fun decodeExpirySeconds(config: ByteArray): Long {
        if (config.size < SIZE) return 0L
        var v = 0L
        for (i in 7 downTo 0) {
            v = (v shl 8) or (config[8 + i].toLong() and 0xff)
        }
        return v
    }
}
