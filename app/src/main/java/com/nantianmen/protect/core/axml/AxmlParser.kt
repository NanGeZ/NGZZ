package com.nantianmen.protect.core.axml

import java.nio.ByteBuffer
import java.nio.ByteOrder

class AxmlNode(
    val name: String,
    val attributes: MutableMap<String, AxmlAttr> = mutableMapOf(),
    val children: MutableList<AxmlNode> = mutableListOf()
) {
    fun find(name: String): AxmlNode? = children.firstOrNull { it.name == name }
    fun findAll(name: String): List<AxmlNode> = children.filter { it.name == name }
    fun attr(name: String): String? = attributes[name]?.value
}

class AxmlAttr(val key: String, val type: Int, val data: Int, val raw: Int, val value: String)

object AxmlParser {

    private const val RES_XML_TYPE = 0x0003
    private const val RES_STRING_POOL_TYPE = 0x0001
    private const val RES_XML_START_ELEMENT_TYPE = 0x0102
    private const val RES_XML_END_ELEMENT_TYPE = 0x0103
    private const val RES_XML_RESOURCE_MAP_TYPE = 0x0180

    private const val TYPE_NULL = 0x00
    private const val TYPE_REFERENCE = 0x01
    private const val TYPE_ATTRIBUTE = 0x02
    private const val TYPE_STRING = 0x03
    private const val TYPE_FLOAT = 0x04
    private const val TYPE_DIMENSION = 0x05
    private const val TYPE_FRACTION = 0x06
    private const val TYPE_INT_DEC = 0x10
    private const val TYPE_INT_HEX = 0x11
    private const val TYPE_INT_BOOLEAN = 0x12

    private val ANDROID_NS = "http://schemas.android.com/apk/res/android"

    fun parse(data: ByteArray): AxmlNode {
        val buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN)
        val type = buf.short.toInt() and 0xFFFF
        buf.short
        val fileSize = buf.int
        require(type == RES_XML_TYPE) { "Not a binary XML document (type=0x${type.toString(16)})" }
        require(fileSize <= data.size) { "Truncated binary XML" }

        var strings: List<String> = emptyList()
        val stack = ArrayDeque<AxmlNode>()
        var root: AxmlNode? = null

        while (buf.position() < fileSize) {
            val chunkStart = buf.position()
            val chunkType = buf.short.toInt() and 0xFFFF
            val headerSize = buf.short.toInt() and 0xFFFF
            val chunkSize = buf.int
            if (chunkSize <= 0 || chunkStart + chunkSize > data.size) break

            when (chunkType) {
                RES_STRING_POOL_TYPE -> {
                    strings = parseStringPool(buf, chunkStart)
                    buf.position(chunkStart + chunkSize)
                }
                RES_XML_RESOURCE_MAP_TYPE -> {
                    buf.position(chunkStart + chunkSize)
                }
                RES_XML_START_ELEMENT_TYPE -> {
                    val line = buf.int
                    buf.int
                    val nsIdx = buf.int
                    val nameIdx = buf.int
                    val attrStart = buf.short.toInt() and 0xFFFF
                    val attrSize = buf.short.toInt() and 0xFFFF
                    val attrCount = buf.short.toInt() and 0xFFFF
                    buf.short
                    buf.short
                    buf.short

                    val node = AxmlNode(str(strings, nameIdx))
                    val attrBase = chunkStart + headerSize + attrStart
                    for (i in 0 until attrCount) {
                        val p = attrBase + i * attrSize
                        val aNs = readInt(data, p)
                        val aName = readInt(data, p + 4)
                        val aRaw = readInt(data, p + 8)
                        val aType = (readInt(data, p + 12) ushr 24) and 0xFF
                        val aData = readInt(data, p + 16)
                        val key = if (aNs == -1) str(strings, aName)
                        else "${nsPrefix(str(strings, aNs))}${str(strings, aName)}"
                        val value = formatValue(strings, aType, aData, aRaw)
                        node.attributes[key] = AxmlAttr(key, aType, aData, aRaw, value)
                    }
                    if (stack.isEmpty()) root = node else stack.last().children.add(node)
                    stack.addLast(node)
                    buf.position(chunkStart + chunkSize)
                }
                RES_XML_END_ELEMENT_TYPE -> {
                    if (stack.isNotEmpty()) stack.removeLast()
                    buf.position(chunkStart + chunkSize)
                }
                else -> buf.position(chunkStart + chunkSize)
            }
        }
        return root ?: AxmlNode("manifest")
    }

    private fun parseStringPool(buf: ByteBuffer, chunkStart: Int): List<String> {
        val start = buf.position()
        val stringCount = buf.int
        val styleCount = buf.int
        val flags = buf.int
        val stringsStart = buf.int
        buf.int
        val utf8 = (flags and (1 shl 8)) != 0
        val large = (flags and (1 shl 9)) != 0
        val offsets = IntArray(stringCount)
        for (i in 0 until stringCount) offsets[i] = buf.int

        val result = ArrayList<String>(stringCount)
        val base = chunkStart + stringsStart
        for (i in 0 until stringCount) {
            var p = base + offsets[i]
            val s: String
            if (utf8) {
                var len = data(buf, p).toInt() and 0xFF
                if (len and 0x80 != 0) {
                    len = ((len and 0x7F) shl 8) or (data(buf, p + 1).toInt() and 0xFF)
                    p += 2
                } else p += 1
                var charLen = data(buf, p).toInt() and 0xFF
                if (charLen and 0x80 != 0) {
                    charLen = ((charLen and 0x7F) shl 8) or (data(buf, p + 1).toInt() and 0xFF)
                    p += 2
                } else p += 1
                s = String(buf.array(), buf.arrayOffset() + p, len, Charsets.UTF_8)
            } else {
                var len = shortAt(buf, p).toInt() and 0xFFFF
                if (len and 0x8000 != 0) {
                    len = ((len and 0x7FFF) shl 16) or (shortAt(buf, p + 2).toInt() and 0xFFFF)
                    p += 4
                } else if (large) {
                    len = buf.getInt(p)
                    p += 4
                } else p += 2
                s = String(buf.array(), buf.arrayOffset() + p, len * 2, Charsets.UTF_16LE)
            }
            result.add(s)
        }
        buf.position(start)
        return result
    }

    private fun data(buf: ByteBuffer, offset: Int): Byte = buf.array()[buf.arrayOffset() + offset]
    private fun shortAt(buf: ByteBuffer, offset: Int): Short =
        (((buf.array()[buf.arrayOffset() + offset + 1].toInt() and 0xFF) shl 8) or
                (buf.array()[buf.arrayOffset() + offset].toInt() and 0xFF)).toShort()

    private fun readInt(data: ByteArray, p: Int): Int =
        (data[p].toInt() and 0xFF) or
                ((data[p + 1].toInt() and 0xFF) shl 8) or
                ((data[p + 2].toInt() and 0xFF) shl 16) or
                ((data[p + 3].toInt() and 0xFF) shl 24)

    private fun str(pool: List<String>, idx: Int): String =
        if (idx in pool.indices) pool[idx] else ""

    private fun nsPrefix(ns: String): String = when {
        ns == ANDROID_NS -> "android:"
        ns.isEmpty() -> ""
        else -> "ns:"
    }

    private fun formatValue(pool: List<String>, type: Int, data: Int, raw: Int): String {
        return when (type) {
            TYPE_STRING -> str(pool, raw)
            TYPE_INT_BOOLEAN -> if (data != 0) "true" else "false"
            TYPE_INT_DEC -> data.toString()
            TYPE_INT_HEX -> "0x" + Integer.toHexString(data)
            TYPE_REFERENCE -> "@0x" + Integer.toHexString(data)
            TYPE_ATTRIBUTE -> "?0x" + Integer.toHexString(data)
            TYPE_FLOAT -> Float.fromBits(data).toString()
            TYPE_NULL -> ""
            TYPE_DIMENSION, TYPE_FRACTION -> data.toString()
            else -> if (raw != -1) str(pool, raw) else "0x" + Integer.toHexString(data)
        }
    }
}
