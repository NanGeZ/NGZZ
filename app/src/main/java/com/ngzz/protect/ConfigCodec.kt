package com.ngzz.protect

object ConfigCodec {

    fun build(switches: Map<String, Boolean>): ByteArray {
        val flags = switches.entries.fold(0) { acc, e ->
            val bit = Protection.entries.indexOfFirst { it.id == e.key }
            if (e.value && bit >= 0) acc or (1 shl bit) else acc
        }
        return byteArrayOf(
            (flags and 0xff).toByte(),
            ((flags ushr 8) and 0xff).toByte(),
            ((flags ushr 16) and 0xff).toByte(),
            ((flags ushr 24) and 0xff).toByte()
        )
    }

    fun decode(config: ByteArray): Int {
        return (config[0].toInt() and 0xff) or
            ((config[1].toInt() and 0xff) shl 8) or
            ((config[2].toInt() and 0xff) shl 16) or
            ((config[3].toInt() and 0xff) shl 24)
    }
}
