package com.nantianmen.protect.core.protect

import java.io.ByteArrayOutputStream

object DexEncoder {

    class Builder {
        internal val protoMap = LinkedHashMap<String, Int>()
        internal val fieldMap = LinkedHashMap<String, Int>()
        internal val methodMap = LinkedHashMap<String, Int>()
        internal val extraStrings = LinkedHashSet<String>()
        internal var finalStrings: Map<String, Int>? = null
        internal val classDefs = mutableListOf<ClassDef>()

        fun stringIndex(s: String): Int {
            extraStrings.add(s)
            return 0
        }

        fun proto(shorty: String, ret: String, params: List<String>): Int =
            protoMap.getOrPut("$shorty|$ret|${params.joinToString(",")}") { protoMap.size }

        fun method(defining: String, name: String, protoIdx: Int): Int =
            methodMap.getOrPut("$defining|$name|$protoIdx") { methodMap.size }

        fun field(defining: String, name: String, type: String): Int =
            fieldMap.getOrPut("$defining|$name|$type") { fieldMap.size }

        fun addClass(
            type: String,
            superclass: String,
            staticFields: List<EncodedField> = emptyList(),
            instanceFields: List<EncodedField> = emptyList(),
            directMethods: List<EncodedMethod> = emptyList(),
            virtualMethods: List<EncodedMethod> = emptyList()
        ) {
            val sortedStatics = staticFields.sortedBy { it.fieldIdx }
            val sortedInstances = instanceFields.sortedBy { it.fieldIdx }
            val sortedDirects = directMethods.sortedBy { it.methodIdx }
            val sortedVirtuals = virtualMethods.sortedBy { it.methodIdx }
            classDefs.add(ClassDef(type, superclass, sortedStatics, sortedInstances, sortedDirects, sortedVirtuals))
        }
    }

    class ClassDef(
        val type: String,
        val superclass: String,
        val staticFields: List<EncodedField>,
        val instanceFields: List<EncodedField>,
        val directMethods: List<EncodedMethod>,
        val virtualMethods: List<EncodedMethod>
    )

    class EncodedField(val fieldIdx: Int, val accessFlags: Int)

    class EncodedMethod(val methodIdx: Int, val accessFlags: Int, val code: ByteArray, val isFinal: Boolean = false)

    const val ACC_PUBLIC = 0x1
    const val ACC_PRIVATE = 0x2
    const val ACC_PROTECTED = 0x4
    const val ACC_STATIC = 0x8
    const val ACC_FINAL = 0x10
    const val ACC_NATIVE = 0x100
    const val ACC_CONSTRUCTOR = 0x10000

    fun build(builder: Builder): ByteArray {
        val stringSet = LinkedHashMap<String, Unit>()
        fun addStr(s: String) {
            stringSet[s] = Unit
        }

        val protos = builder.protoMap.keys.map { key ->
            val parts = key.split("|", limit = 3)
            Proto(parts[0], parts[1], if (parts[2].isEmpty()) emptyList() else parts[2].split(","))
        }
        protos.forEach {
            addStr(it.shorty)
            addStr(it.ret)
            it.params.forEach { p -> addStr(p) }
        }

        val methods = builder.methodMap.keys.map { key ->
            val parts = key.split("|")
            Method(parts[0], parts[1], parts[2].toInt())
        }
        val fields = builder.fieldMap.keys.map { key ->
            val parts = key.split("|")
            Field(parts[0], parts[1], parts[2])
        }
        methods.forEach {
            addStr(it.defining)
            addStr(it.name)
        }
        fields.forEach {
            addStr(it.defining)
            addStr(it.name)
            addStr(it.type)
        }

        builder.classDefs.forEach {
            addStr(it.type)
            addStr(it.superclass)
        }
        builder.extraStrings.forEach { addStr(it) }
        addStr("Landroid/app/Application;")
        addStr("Landroid/content/Context;")

        val strings = stringSet.keys.sorted()
        val stringIdx = HashMap<String, Int>(strings.size).also { m -> strings.forEachIndexed { i, s -> m[s] = i } }
        builder.finalStrings = stringIdx
        val types = (protos.map { it.ret } + protos.flatMap { it.params } +
                methods.map { it.defining } + fields.map { it.defining } + fields.map { it.type } +
                builder.classDefs.map { it.type } + builder.classDefs.map { it.superclass })
            .distinct().sorted()
        val typeIdx = HashMap<String, Int>(types.size).also { m -> types.forEachIndexed { i, s -> m[s] = i } }

        val protoList = protos.map {
            ProtoIdx(stringIdx.getValue(it.shorty), typeIdx.getValue(it.ret), it.params.map { p -> typeIdx.getValue(p) })
        }
        val fieldList = fields.map {
            FieldIdx(typeIdx.getValue(it.defining), typeIdx.getValue(it.type), stringIdx.getValue(it.name))
        }
        val methodList = methods.map {
            MethodIdx(typeIdx.getValue(it.defining), it.protoIdx, stringIdx.getValue(it.name))
        }

        val data = ByteArrayOutputStream()
        fun uleb(v: Int) {
            var value = v
            while (true) {
                if (value and 0x7F.inv() == 0) {
                    data.write(value)
                    return
                }
                data.write((value and 0x7F) or 0x80)
                value = value ushr 7
            }
        }

        val codeItems = mutableListOf<ByteArray>()
        val codeOffTable = mutableListOf<Int>()
        fun codeItemOff(item: ByteArray): Int {
            codeItems.add(item)
            codeOffTable.add(0)
            return codeItems.size - 1
        }

        val typeLists = mutableListOf<List<Int>>()
        fun typeListOff(params: List<Int>): Int {
            if (params.isEmpty()) return 0
            typeLists.add(params)
            return typeLists.size - 1
        }

        val protoTypeListIdx = protoList.map { p -> typeListOff(p.paramIdx) }

        val codeOffPatchPos = mutableListOf<Pair<Int, Int>>()
        val classDataOffs = IntArray(builder.classDefs.size)
        for ((ci, cd) in builder.classDefs.withIndex()) {
            if (cd.staticFields.isEmpty() && cd.instanceFields.isEmpty() && cd.directMethods.isEmpty() && cd.virtualMethods.isEmpty()) {
                classDataOffs[ci] = 0
                continue
            }
            classDataOffs[ci] = data.size()
            uleb(cd.staticFields.size)
            uleb(cd.instanceFields.size)
            uleb(cd.directMethods.size)
            uleb(cd.virtualMethods.size)
            var prev = 0
            for (f in cd.staticFields + cd.instanceFields) {
                uleb(f.fieldIdx - prev)
                prev = f.fieldIdx
                uleb(f.accessFlags)
            }
            prev = 0
            for (m in cd.directMethods) {
                uleb(m.methodIdx - prev)
                prev = m.methodIdx
                uleb(m.accessFlags)
                if (m.code.isEmpty()) uleb(0)
                else {
                    codeOffPatchPos.add(Pair(data.size(), codeItemOff(m.code)))
                    uleb(0)
                }
            }
            prev = 0
            for (m in cd.virtualMethods) {
                uleb(m.methodIdx - prev)
                prev = m.methodIdx
                uleb(m.accessFlags)
                if (m.code.isEmpty()) uleb(0)
                else {
                    codeOffPatchPos.add(Pair(data.size(), codeItemOff(m.code)))
                    uleb(0)
                }
            }
        }

        fun mi(v: Int) = byteArrayOf(
            (v and 0xFF).toByte(), ((v shr 8) and 0xFF).toByte(),
            ((v shr 16) and 0xFF).toByte(), ((v shr 24) and 0xFF).toByte()
        )
        fun ms(v: Int) = byteArrayOf((v and 0xFF).toByte(), ((v shr 8) and 0xFF).toByte())

        val typeListOffs = IntArray(typeLists.size)
        for ((i, tl) in typeLists.withIndex()) {
            typeListOffs[i] = align(data.size(), 4)
            padTo(data, typeListOffs[i])
            data.write(byteArrayOf((tl.size and 0xFF).toByte(), ((tl.size shr 8) and 0xFF).toByte(), 0, 0))
            for (t in tl) data.write(mi(t))
        }

        val codeStart = align(data.size(), 4)
        padTo(data, codeStart)
        for ((i, item) in codeItems.withIndex()) {
            codeOffTable[i] = align(data.size(), 4)
            padTo(data, codeOffTable[i])
            data.write(item)
        }

        val mapListOff = data.size()
        val mapEntries = mutableListOf<MapItem>()
        fun mapItem(type: Int, size: Int, off: Int) {
            if (size > 0) mapEntries.add(MapItem(type, size, off))
        }

        val headerSize = 0x70
        var off = headerSize

        val stringIdsOff = off
        off += strings.size * 4

        val stringDataOffs = IntArray(strings.size)
        for ((i, s) in strings.withIndex()) {
            stringDataOffs[i] = off
            val utf8 = s.toByteArray(Charsets.UTF_8)
            off += uleb128Size(utf8CharLength(s)) + uleb128Size(utf8.size) + utf8.size + 1
        }

        val typeIdsOff = align(off, 4)
        off = typeIdsOff + types.size * 4

        val protoIdsOff = off
        off += protoList.size * 12

        val fieldIdsOff = align(off, 4)
        off = fieldIdsOff + fieldList.size * 8

        val methodIdsOff = off
        off = methodIdsOff + methodList.size * 8

        val classDefsOff = align(off, 4)
        off = classDefsOff + builder.classDefs.size * 32

        val dataOff = align(off, 4)
        val dataStart = dataOff

        mapItem(0x0000, 1, dataStart + mapListOff)
        mapItem(0x0001, strings.size, stringIdsOff)
        mapItem(0x0002, types.size, typeIdsOff)
        mapItem(0x0003, protoList.size, protoIdsOff)
        mapItem(0x0004, fieldList.size, fieldIdsOff)
        mapItem(0x0005, methodList.size, methodIdsOff)
        mapItem(0x0006, builder.classDefs.size, classDefsOff)
        mapItem(0x1001, typeLists.size, if (typeLists.isEmpty()) 0 else dataStart + typeListOffs[0])
        val classDataCount = classDataOffs.count { it != 0 }
        if (classDataCount > 0) {
            mapItem(0x1003, classDataCount, dataStart + classDataOffs.first { it != 0 })
        }
        if (codeItems.isNotEmpty()) {
            mapItem(0x2001, codeItems.size, dataStart + codeStart)
        }
        mapItem(0x1000, 1, dataStart + mapListOff)

        val mapBytes = ByteArrayOutputStream()
        mapBytes.write(mi(mapEntries.size))
        for (e in mapEntries) {
            mapBytes.write(ms(e.type))
            mapBytes.write(ms(0))
            mapBytes.write(mi(e.size))
            mapBytes.write(mi(e.off))
        }

        val dataBytes = data.toByteArray()
        for ((pos, ci) in codeOffPatchPos) {
            val absOff = dataStart + codeOffTable[ci]
            var p = pos
            var v = absOff
            while (true) {
                if (v and 0x7F.inv() == 0) {
                    dataBytes[p] = v.toByte()
                    break
                }
                dataBytes[p] = ((v and 0x7F) or 0x80).toByte()
                v = v ushr 7
                p++
            }
        }

        val fileSize = dataStart + data.size() + mapBytes.size()

        val out = ByteArrayOutputStream(fileSize)
        out.write(byteArrayOf(0x64, 0x65, 0x78, 0x0A, 0x30, 0x33, 0x35, 0x00))
        out.write(ByteArray(24))
        out.write(mi(fileSize))
        out.write(mi(headerSize))
        out.write(mi(0x12345678))
        out.write(mi(0))                            // link_size
        out.write(mi(0))                            // link_off
        out.write(mi(dataStart + mapListOff))       // map_off
        out.write(mi(strings.size))
        out.write(mi(stringIdsOff))
        out.write(mi(types.size))
        out.write(mi(typeIdsOff))
        out.write(mi(protoList.size))
        out.write(mi(protoIdsOff))
        out.write(mi(fieldList.size))
        out.write(mi(fieldIdsOff))
        out.write(mi(methodList.size))
        out.write(mi(methodIdsOff))
        out.write(mi(builder.classDefs.size))
        out.write(mi(classDefsOff))
        out.write(mi(data.size() + mapBytes.size()))
        out.write(mi(dataOff))

        for (o in stringDataOffs) out.write(mi(o))
        for (s in strings) {
            val utf8 = s.toByteArray(Charsets.UTF_8)
            writeUleb(out, utf8CharLength(s))
            writeUleb(out, utf8.size)
            out.write(utf8)
            out.write(0)
        }
        padTo(out, typeIdsOff)
        for (t in types) out.write(mi(stringIdx.getValue(t)))
        padTo(out, protoIdsOff)
        for ((pi, p) in protoList.withIndex()) {
            out.write(mi(p.shortyIdx))
            out.write(mi(p.retIdx))
            out.write(mi(if (p.paramIdx.isEmpty()) 0 else dataStart + typeListOffs[protoTypeListIdx[pi]]))
        }
        padTo(out, fieldIdsOff)
        for (f in fieldList) {
            out.write(byteArrayOf((f.classIdx and 0xFF).toByte(), ((f.classIdx shr 8) and 0xFF).toByte()))
            out.write(byteArrayOf((f.typeIdx and 0xFF).toByte(), ((f.typeIdx shr 8) and 0xFF).toByte()))
            out.write(mi(f.nameIdx))
        }
        padTo(out, methodIdsOff)
        for (m in methodList) {
            out.write(byteArrayOf((m.classIdx and 0xFF).toByte(), ((m.classIdx shr 8) and 0xFF).toByte()))
            out.write(byteArrayOf((m.protoIdx and 0xFF).toByte(), ((m.protoIdx shr 8) and 0xFF).toByte()))
            out.write(mi(m.nameIdx))
        }
        padTo(out, classDefsOff)
        for ((ci, cd) in builder.classDefs.withIndex()) {
            out.write(mi(typeIdx.getValue(cd.type)))
            out.write(mi(ACC_PUBLIC))
            out.write(mi(typeIdx.getValue(cd.superclass)))
            out.write(mi(0))
            out.write(mi(0))
            out.write(mi(0))
            out.write(mi(if (classDataOffs[ci] == 0) 0 else dataStart + classDataOffs[ci]))
            out.write(mi(0))
        }
        padTo(out, dataStart)
        out.write(dataBytes)
        out.write(mapBytes.toByteArray())

        val bytes = out.toByteArray()
        val sig = sha1(bytes, 32, bytes.size - 32)
        System.arraycopy(sig, 0, bytes, 12, 20)
        val checksum = adler32(bytes, 12, bytes.size - 12)
        System.arraycopy(mi(checksum), 0, bytes, 8, 4)
        return bytes
    }

    class Proto(val shorty: String, val ret: String, val params: List<String>)
    class ProtoIdx(val shortyIdx: Int, val retIdx: Int, val paramIdx: List<Int>)
    class Field(val defining: String, val name: String, val type: String)
    class FieldIdx(val classIdx: Int, val typeIdx: Int, val nameIdx: Int)
    class Method(val defining: String, val name: String, val protoIdx: Int)
    class MethodIdx(val classIdx: Int, val protoIdx: Int, val nameIdx: Int)
    class MapItem(val type: Int, val size: Int, val off: Int)

    private fun padTo(out: ByteArrayOutputStream, target: Int) {
        while (out.size() < target) out.write(0)
    }

    private fun uleb128Size(v: Int): Int {
        var value = v
        var size = 1
        while (value and 0x7F.inv() != 0) {
            size++
            value = value ushr 7
        }
        return size
    }

    private fun utf8CharLength(s: String): Int = s.length

    private fun writeUleb(out: ByteArrayOutputStream, v: Int) {
        var value = v
        while (true) {
            if (value and 0x7F.inv() == 0) {
                out.write(value)
                return
            }
            out.write((value and 0x7F) or 0x80)
            value = value ushr 7
        }
    }

    private fun align(v: Int, a: Int): Int = (v + a - 1) and (a - 1).inv()

    private fun adler32(data: ByteArray, off: Int, len: Int): Int {
        var a = 1
        var b = 0
        for (i in off until off + len) {
            a = (a + (data[i].toInt() and 0xFF)) % 65521
            b = (b + a) % 65521
        }
        return (b shl 16) or a
    }

    private fun sha1(data: ByteArray, off: Int, len: Int): ByteArray {
        val md = java.security.MessageDigest.getInstance("SHA-1")
        md.update(data, off, len)
        return md.digest()
    }
}
