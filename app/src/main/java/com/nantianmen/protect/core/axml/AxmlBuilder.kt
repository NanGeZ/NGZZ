package com.nantianmen.protect.core.axml

import java.io.ByteArrayOutputStream

class XmlElement(val name: String) {
    val attrs = LinkedHashMap<String, AttrValue>()
    val children = mutableListOf<XmlElement>()

    fun attr(name: String, value: String) {
        attrs[name] = AttrValue.StringValue(value)
    }

    fun attr(name: String, value: Int) {
        attrs[name] = AttrValue.IntValue(value)
    }

    fun attr(name: String, value: Boolean) {
        attrs[name] = AttrValue.BoolValue(value)
    }

    fun rawAttr(name: String, type: Int, data: Int, raw: String) {
        attrs[name] = AttrValue.TypedValue(type, data, raw)
    }

    fun child(name: String, block: XmlElement.() -> Unit = {}): XmlElement {
        val e = XmlElement(name)
        e.block()
        children.add(e)
        return e
    }
}

sealed class AttrValue {
    data class StringValue(val v: String) : AttrValue()
    data class IntValue(val v: Int) : AttrValue()
    data class BoolValue(val v: Boolean) : AttrValue()
    data class TypedValue(val type: Int, val data: Int, val raw: String) : AttrValue()
}

object AxmlBuilder {

    private const val RES_STRING_POOL_TYPE = 0x0001
    private const val RES_XML_TYPE = 0x0003
    private const val RES_XML_START_NAMESPACE_TYPE = 0x0100
    private const val RES_XML_END_NAMESPACE_TYPE = 0x0101
    private const val RES_XML_START_ELEMENT_TYPE = 0x0102
    private const val RES_XML_END_ELEMENT_TYPE = 0x0103

    private const val ANDROID_NS = "http://schemas.android.com/apk/res/android"

    private const val TYPE_STRING = 0x03
    private const val TYPE_INT_DEC = 0x10
    private const val TYPE_INT_BOOLEAN = 0x12

    fun build(root: XmlElement): ByteArray {
        val strings = LinkedHashMap<String, Int>()
        fun idx(s: String): Int = strings.getOrPut(s) { strings.size }

        collectStrings(root, ::idx)
        idx("android")
        idx(ANDROID_NS)

        val stringPool = buildStringPool(strings.keys.toList())
        val body = ByteArrayOutputStream()

        body.write(namespaceChunk(RES_XML_START_NAMESPACE_TYPE, idx("android"), idx(ANDROID_NS)))
        writeElement(body, root, ::idx)
        body.write(namespaceChunk(RES_XML_END_NAMESPACE_TYPE, idx("android"), idx(ANDROID_NS)))

        val bodyBytes = body.toByteArray()
        val total = 8 + stringPool.size + bodyBytes.size
        val out = ByteArrayOutputStream(total)
        out.write(short(RES_XML_TYPE))
        out.write(short(8))
        out.write(int(total))
        out.write(stringPool)
        out.write(bodyBytes)
        return out.toByteArray()
    }

    private fun collectStrings(e: XmlElement, idx: (String) -> Int) {
        idx(e.name)
        e.attrs.forEach { (k, v) ->
            idx(k)
            when (v) {
                is AttrValue.StringValue -> idx(v.v)
                is AttrValue.TypedValue -> if (v.type == TYPE_STRING) idx(v.raw)
                else -> {}
            }
        }
        e.children.forEach { collectStrings(it, idx) }
    }

    private fun writeElement(out: ByteArrayOutputStream, e: XmlElement, idx: (String) -> Int) {
        val attrs = ByteArrayOutputStream()
        e.attrs.forEach { (k, v) ->
            attrs.write(int(-1))
            attrs.write(int(idx(k)))
            when (v) {
                is AttrValue.StringValue -> {
                    attrs.write(int(idx(v.v)))
                    attrs.write(int((TYPE_STRING shl 24) or 8))
                    attrs.write(int(idx(v.v)))
                }
                is AttrValue.BoolValue -> {
                    attrs.write(int(-1))
                    attrs.write(int((TYPE_INT_BOOLEAN shl 24)))
                    attrs.write(int(if (v.v) -1 else 0))
                }
                is AttrValue.IntValue -> {
                    attrs.write(int(-1))
                    attrs.write(int((TYPE_INT_DEC shl 24)))
                    attrs.write(int(v.v))
                }
                is AttrValue.TypedValue -> {
                    if (v.type == TYPE_STRING) {
                        attrs.write(int(idx(v.raw)))
                        attrs.write(int((TYPE_STRING shl 24) or 8))
                        attrs.write(int(idx(v.raw)))
                    } else {
                        attrs.write(int(-1))
                        attrs.write(int((v.type shl 24)))
                        attrs.write(int(v.data))
                    }
                }
            }
        }
        val attrBytes = attrs.toByteArray()
        val body = ByteArrayOutputStream()
        body.write(int(-1))
        body.write(int(idx(e.name)))
        body.write(short(0x14))
        body.write(short(20))
        body.write(short(e.attrs.size))
        body.write(short(0))
        body.write(short(0))
        body.write(short(0))
        body.write(attrBytes)

        out.write(typeChunk(RES_XML_START_ELEMENT_TYPE, body.toByteArray()))

        e.children.forEach { writeElement(out, it, idx) }

        val endBody = ByteArrayOutputStream()
        endBody.write(int(-1))
        endBody.write(int(idx(e.name)))
        out.write(typeChunk(RES_XML_END_ELEMENT_TYPE, endBody.toByteArray()))
    }

    private fun namespaceChunk(type: Int, prefixIdx: Int, uriIdx: Int): ByteArray {
        val body = ByteArrayOutputStream()
        body.write(int(1))
        body.write(int(-1))
        body.write(int(prefixIdx))
        body.write(int(uriIdx))
        return typeChunk(type, body.toByteArray())
    }

    private fun typeChunk(type: Int, body: ByteArray): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(short(type))
        out.write(short(8))
        out.write(int(8 + body.size))
        out.write(body)
        return out.toByteArray()
    }

    private fun buildStringPool(strings: List<String>): ByteArray {
        val offsets = IntArray(strings.size)
        val data = ByteArrayOutputStream()
        var off = 0
        strings.forEachIndexed { i, s ->
            offsets[i] = off
            val utf16 = s.toByteArray(Charsets.UTF_16LE)
            val len = s.length
            val header = if (len > 0x7FFF) {
                byteArrayOf(
                    ((len ushr 16) or 0x80).toByte(), ((len ushr 24) and 0xFF).toByte(),
                    (len and 0xFF).toByte(), ((len ushr 8) and 0xFF).toByte()
                )
            } else {
                byteArrayOf((len and 0xFF).toByte(), ((len ushr 8) and 0xFF).toByte())
            }
            data.write(header)
            data.write(utf16)
            data.write(byteArrayOf(0, 0))
            off += header.size + utf16.size + 2
        }
        val dataBytes = data.toByteArray()
        val headerSize = 28
        val offsetsSize = strings.size * 4
        val stringsStart = headerSize + offsetsSize
        val chunkSize = stringsStart + dataBytes.size

        val out = ByteArrayOutputStream(chunkSize)
        out.write(short(RES_STRING_POOL_TYPE))
        out.write(short(headerSize))
        out.write(int(chunkSize))
        out.write(int(strings.size))
        out.write(int(0))
        out.write(int(0))
        out.write(int(stringsStart))
        out.write(int(0))
        offsets.forEach { out.write(int(it)) }
        out.write(dataBytes)
        return out.toByteArray()
    }

    private fun short(v: Int): ByteArray = byteArrayOf((v and 0xFF).toByte(), ((v shr 8) and 0xFF).toByte())
    private fun int(v: Int): ByteArray = byteArrayOf(
        (v and 0xFF).toByte(),
        ((v shr 8) and 0xFF).toByte(),
        ((v shr 16) and 0xFF).toByte(),
        ((v shr 24) and 0xFF).toByte()
    )
}
