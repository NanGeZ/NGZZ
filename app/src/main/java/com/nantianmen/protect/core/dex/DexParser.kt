package com.nantianmen.protect.core.dex

import com.nantianmen.protect.core.model.DexInfo
import java.nio.ByteBuffer
import java.nio.ByteOrder

object DexParser {

    private val DEX_MAGIC = byteArrayOf(0x64, 0x65, 0x78, 0x0A)

    fun parse(data: ByteArray, name: String): DexInfo {
        if (data.size < 0x70 || !data.copyOfRange(0, 4).contentEquals(DEX_MAGIC)) {
            return DexInfo(name, 0, 0, false)
        }
        val buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN)
        buf.position(56)
        val stringIdsSize = buf.int
        buf.int
        val typeIdsSize = buf.int
        buf.int
        val protoIdsSize = buf.int
        buf.int
        val fieldIdsSize = buf.int
        buf.int
        val methodIdsSize = buf.int
        buf.int
        val classDefsSize = buf.int

        return DexInfo(
            name = name,
            classCount = classDefsSize,
            methodCount = methodIdsSize,
            hasMultiDex = classDefsSize > 65536
        )
    }

    fun isValidDex(data: ByteArray): Boolean =
        data.size >= 0x70 && data.copyOfRange(0, 4).contentEquals(DEX_MAGIC)
}
