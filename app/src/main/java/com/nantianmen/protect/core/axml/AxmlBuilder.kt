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
    private const val RES_XML_RESOURCE_MAP_TYPE = 0x0180

    private const val ANDROID_NS = "http://schemas.android.com/apk/res/android"

    private const val TYPE_STRING = 0x03
    private const val TYPE_INT_DEC = 0x10
    private const val TYPE_INT_BOOLEAN = 0x12

    /**
     * Android 框架属性名 -> 资源 ID（经 aapt2 对 android-35 提取校验）。
     * 这些字符串必须位于字符串池开头，紧随其后的资源映射表按索引一一对应；
     * PackageManager / apksigner 通过资源 ID 读取 minSdkVersion 等关键属性。
     */
    private val ANDROID_ATTR_IDS = listOf(
        "theme" to 0x01010000,
        "label" to 0x01010001,
        "icon" to 0x01010002,
        "name" to 0x01010003,
        "allowClearUserData" to 0x01010005,
        "permission" to 0x01010006,
        "readPermission" to 0x01010007,
        "writePermission" to 0x01010008,
        "protectionLevel" to 0x01010009,
        "sharedUserId" to 0x0101000B,
        "enabled" to 0x0101000E,
        "debuggable" to 0x0101000F,
        "exported" to 0x01010010,
        "process" to 0x01010011,
        "taskAffinity" to 0x01010012,
        "multiprocess" to 0x01010013,
        "finishOnTaskLaunch" to 0x01010014,
        "clearTaskOnLaunch" to 0x01010015,
        "stateNotNeeded" to 0x01010016,
        "excludeFromRecents" to 0x01010017,
        "authorities" to 0x01010018,
        "syncable" to 0x01010019,
        "initOrder" to 0x0101001A,
        "grantUriPermissions" to 0x0101001B,
        "priority" to 0x0101001C,
        "launchMode" to 0x0101001D,
        "screenOrientation" to 0x0101001E,
        "configChanges" to 0x0101001F,
        "value" to 0x01010024,
        "resource" to 0x01010025,
        "mimeType" to 0x01010026,
        "scheme" to 0x01010027,
        "host" to 0x01010028,
        "port" to 0x01010029,
        "path" to 0x0101002A,
        "pathPrefix" to 0x0101002B,
        "pathPattern" to 0x0101002C,
        "targetActivity" to 0x01010202,
        "allowTaskReparenting" to 0x01010204,
        "minSdkVersion" to 0x0101020C,
        "versionCode" to 0x0101021B,
        "versionName" to 0x0101021C,
        "windowSoftInputMode" to 0x0101022B,
        "noHistory" to 0x0101022D,
        "targetSdkVersion" to 0x01010270,
        "maxSdkVersion" to 0x01010271,
        "allowBackup" to 0x01010280,
        "glEsVersion" to 0x01010281,
        "required" to 0x0101028E,
        "finishOnCloseSystemDialogs" to 0x010102A7,
        "installLocation" to 0x010102B7,
        "logo" to 0x010102BE,
        "hardwareAccelerated" to 0x010102D3,
        "largeHeap" to 0x0101035A,
        "stopWithTask" to 0x0101036A,
        "parentActivityName" to 0x010103A7,
        "isolatedProcess" to 0x010103A9,
        "supportsRtl" to 0x010103AF,
        "autoRemoveFromRecents" to 0x01010447,
        "resumeWhilePausing" to 0x010104B2,
        "usesCleartextTraffic" to 0x010104EC,
        "resizeableActivity" to 0x010104F6,
        "supportsPictureInPicture" to 0x010104F7,
        "directBootAware" to 0x01010505,
        "roundIcon" to 0x0101052C,
        "showWhenLocked" to 0x01010569,
        "turnScreenOn" to 0x0101056A,
        "compileSdkVersion" to 0x01010572,
        "compileSdkVersionCodename" to 0x01010573,
        "foregroundServiceType" to 0x01010599,
        "zygotePreloadName" to 0x0101059D,
        "requestLegacyExternalStorage" to 0x01010603
    )

    fun build(root: XmlElement): ByteArray {
        val strings = LinkedHashMap<String, Int>()
        fun idx(s: String): Int = strings.getOrPut(s) { strings.size }

        // 框架属性名置顶，保证资源映射表（按字符串索引）与之对应
        ANDROID_ATTR_IDS.forEach { (name, _) -> idx(name) }
        collectStrings(root, ::idx)
        idx("android")
        idx(ANDROID_NS)

        val stringPool = buildStringPool(strings.keys.toList())
        val resourceMap = buildResourceMap()
        val body = ByteArrayOutputStream()

        body.write(namespaceChunk(RES_XML_START_NAMESPACE_TYPE, idx("android"), idx(ANDROID_NS)))
        writeElement(body, root, ::idx)
        body.write(namespaceChunk(RES_XML_END_NAMESPACE_TYPE, idx("android"), idx(ANDROID_NS)))

        val bodyBytes = body.toByteArray()
        val total = 8 + stringPool.size + resourceMap.size + bodyBytes.size
        val out = ByteArrayOutputStream(total)
        out.write(short(RES_XML_TYPE))
        out.write(short(8))
        out.write(int(total))
        out.write(stringPool)
        out.write(resourceMap)
        out.write(bodyBytes)
        return out.toByteArray()
    }

    private fun buildResourceMap(): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(short(RES_XML_RESOURCE_MAP_TYPE))
        out.write(short(8))
        out.write(int(8 + ANDROID_ATTR_IDS.size * 4))
        ANDROID_ATTR_IDS.forEach { (_, id) -> out.write(int(id)) }
        return out.toByteArray()
    }

    private fun collectStrings(e: XmlElement, idx: (String) -> Int) {
        idx(e.name)
        e.attrs.forEach { (k, v) ->
            if (k.startsWith("android:")) idx(k.substring("android:".length)) else idx(k)
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
            val isAndroid = k.startsWith("android:")
            attrs.write(int(if (isAndroid) idx(ANDROID_NS) else -1))
            attrs.write(int(if (isAndroid) idx(k.substring("android:".length)) else idx(k)))
            when (v) {
                is AttrValue.StringValue -> {
                    attrs.write(int(idx(v.v)))
                    attrs.write(int((TYPE_STRING shl 24) or 8))
                    attrs.write(int(idx(v.v)))
                }
                is AttrValue.BoolValue -> {
                    attrs.write(int(-1))
                    attrs.write(int((TYPE_INT_BOOLEAN shl 24) or 8))
                    attrs.write(int(if (v.v) -1 else 0))
                }
                is AttrValue.IntValue -> {
                    attrs.write(int(-1))
                    attrs.write(int((TYPE_INT_DEC shl 24) or 8))
                    attrs.write(int(v.v))
                }
                is AttrValue.TypedValue -> {
                    if (v.type == TYPE_STRING) {
                        attrs.write(int(idx(v.raw)))
                        attrs.write(int((TYPE_STRING shl 24) or 8))
                        attrs.write(int(idx(v.raw)))
                    } else {
                        attrs.write(int(-1))
                        attrs.write(int((v.type shl 24) or 8))
                        attrs.write(int(v.data))
                    }
                }
            }
        }
        val attrBytes = attrs.toByteArray()
        val body = ByteArrayOutputStream()
        body.write(int(1))          // lineNumber
        body.write(int(-1))         // comment
        body.write(int(-1))         // ns
        body.write(int(idx(e.name)))
        body.write(short(0x14))     // attributeStart (from start of attrExt = 20)
        body.write(short(20))       // attributeSize
        body.write(short(e.attrs.size))
        body.write(short(0))
        body.write(short(0))
        body.write(short(0))
        body.write(attrBytes)

        out.write(typeChunk(RES_XML_START_ELEMENT_TYPE, body.toByteArray()))

        e.children.forEach { writeElement(out, it, idx) }

        val endBody = ByteArrayOutputStream()
        endBody.write(int(1))       // lineNumber
        endBody.write(int(-1))      // comment
        endBody.write(int(-1))      // ns
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
        out.write(short(16))        // headerSize: ResXMLTree_node = 16
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
