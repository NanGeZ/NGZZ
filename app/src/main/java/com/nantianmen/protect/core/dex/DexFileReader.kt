package com.nantianmen.protect.core.dex

import java.nio.ByteBuffer
import java.nio.ByteOrder

class DexClassDef(
    val classIdx: Int,
    val accessFlags: Int,
    val superclassIdx: Int,
    val sourceFileIdx: Int,
    val classDataOff: Int,
    val staticValuesOff: Int
)

class DexMethodRef(val classIdx: Int, val protoIdx: Int, val nameIdx: Int)

class DexFile(
    val strings: List<String>,
    val types: List<String>,
    val protos: List<String>,
    val fields: List<String>,
    val methods: List<DexMethodRef>,
    val classDefs: List<DexClassDef>,
    val fileChecksum: Int
) {
    fun className(classIdx: Int): String = types.getOrElse(classIdx) { "?" }
    fun methodName(m: DexMethodRef): String = "${className(m.classIdx)}->${strings.getOrElse(m.nameIdx) { "?" }}"
}

object DexFileReader {

    fun read(data: ByteArray): DexFile {
        require(data.size >= 0x70) { "DEX too small" }
        require(data[0] == 'd'.code.toByte() && data[1] == 'e'.code.toByte() && data[2] == 'x'.code.toByte()) {
            "Not a DEX file"
        }
        val buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN)
        buf.position(56)
        val stringIdsSize = buf.int
        val stringIdsOff = buf.int
        val typeIdsSize = buf.int
        val typeIdsOff = buf.int
        val protoIdsSize = buf.int
        val protoIdsOff = buf.int
        val fieldIdsSize = buf.int
        val fieldIdsOff = buf.int
        val methodIdsSize = buf.int
        val methodIdsOff = buf.int
        val classDefsSize = buf.int
        val classDefsOff = buf.int

        val strings = ArrayList<String>(stringIdsSize)
        for (i in 0 until stringIdsSize) {
            val off = buf.getInt(stringIdsOff + i * 4)
            strings.add(readUlebString(data, off))
        }

        val types = ArrayList<String>(typeIdsSize)
        for (i in 0 until typeIdsSize) {
            val idx = buf.getInt(typeIdsOff + i * 4)
            types.add(strings.getOrElse(idx) { "?" })
        }

        val protos = ArrayList<String>(protoIdsSize)
        for (i in 0 until protoIdsSize) {
            val shortyIdx = buf.getInt(protoIdsOff + i * 12)
            val retIdx = buf.getInt(protoIdsOff + i * 12 + 4)
            val paramsOff = buf.getInt(protoIdsOff + i * 12 + 8)
            val params = mutableListOf<String>()
            if (paramsOff != 0) {
                val size = buf.getInt(paramsOff)
                for (j in 0 until size) {
                    val t = buf.getShort(paramsOff + 4 + j * 2).toInt() and 0xFFFF
                    params.add(types.getOrElse(t) { "?" })
                }
            }
            protos.add("(${params.joinToString(",")})${types.getOrElse(retIdx) { "?" }}")
        }

        val fields = ArrayList<String>(fieldIdsSize)
        for (i in 0 until fieldIdsSize) {
            val classIdx = buf.getShort(fieldIdsOff + i * 8).toInt() and 0xFFFF
            val typeIdx = buf.getShort(fieldIdsOff + i * 8 + 2).toInt() and 0xFFFF
            val nameIdx = buf.getInt(fieldIdsOff + i * 8 + 4)
            fields.add("${types.getOrElse(classIdx) { "?" }}->${strings.getOrElse(nameIdx) { "?" }}:${types.getOrElse(typeIdx) { "?" }}")
        }

        val methods = ArrayList<DexMethodRef>(methodIdsSize)
        for (i in 0 until methodIdsSize) {
            val classIdx = buf.getShort(methodIdsOff + i * 8).toInt() and 0xFFFF
            val protoIdx = buf.getShort(methodIdsOff + i * 8 + 2).toInt() and 0xFFFF
            val nameIdx = buf.getInt(methodIdsOff + i * 8 + 4)
            methods.add(DexMethodRef(classIdx, protoIdx, nameIdx))
        }

        val classDefs = ArrayList<DexClassDef>(classDefsSize)
        for (i in 0 until classDefsSize) {
            val base = classDefsOff + i * 32
            classDefs.add(
                DexClassDef(
                    buf.getInt(base),
                    buf.getInt(base + 4),
                    buf.getInt(base + 8),
                    buf.getInt(base + 12),
                    buf.getInt(base + 20),
                    buf.getInt(base + 24)
                )
            )
        }

        return DexFile(strings, types, protos, fields, methods, classDefs, 0)
    }

    private fun readUlebString(data: ByteArray, off: Int): String {
        var p = off
        // string_data_item = uleb128(utf16_size) + MUTF-8 字节 + 0x00，没有字节长度字段
        while (data[p].toInt() and 0x80 != 0) p++
        p++
        val start = p
        while (data[p].toInt() != 0) p++
        return String(data, start, p - start, Charsets.UTF_8)
    }
}
