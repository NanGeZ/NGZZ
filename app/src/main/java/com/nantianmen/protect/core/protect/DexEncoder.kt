package com.nantianmen.protect.core.protect

import java.io.ByteArrayOutputStream

object DexEncoder {

    class Builder {
        internal val protoMap = LinkedHashMap<String, Int>()
        internal val fieldMap = LinkedHashMap<String, Int>()
        internal val methodMap = LinkedHashMap<String, Int>()
        internal val extraStrings = LinkedHashSet<String>()
        internal var finalStrings: Map<String, Int>? = null
        internal var frozen: Frozen? = null
        internal val classDefs = mutableListOf<ClassDef>()

        val isFrozen: Boolean get() = frozen != null

        private fun ensureMutable(what: String) {
            check(frozen == null) { "符号表已定稿（freeze），不能再注册 $what" }
        }

        /** 注册字符串（不取下标）；全部注册完后调用 [freeze] 一次性定稿 */
        fun addString(s: String) {
            ensureMutable("字符串")
            extraStrings.add(s)
        }

        /**
         * 取字符串的最终下标。必须先 [freeze]：
         * 字符串表每新增一条就整体重排，分散取下标会拿到被后续插入顶掉的过期值。
         */
        fun stringIndex(s: String): Int {
            val f = frozen ?: error("请先 addString() 注册全部字符串并调用 freeze()")
            return f.stringIdx[s] ?: error("字符串未入表：$s")
        }

        fun proto(shorty: String, ret: String, params: List<String>): Int {
            ensureMutable("proto")
            return protoMap.getOrPut("$shorty|$ret|${params.joinToString(",")}") { protoMap.size }
        }

        fun method(defining: String, name: String, protoIdx: Int): Int {
            ensureMutable("method")
            return methodMap.getOrPut("$defining|$name|$protoIdx") { methodMap.size }
        }

        fun field(defining: String, name: String, type: String): Int {
            ensureMutable("field")
            return fieldMap.getOrPut("$defining|$name|$type") { fieldMap.size }
        }

        /** 注册序下标 → 表内排序后的最终下标（字节码使用后者） */
        fun finalMethodIdx(regIdx: Int): Int =
            (frozen ?: error("尚未 freeze()")).methodRemap[regIdx]

        fun finalFieldIdx(regIdx: Int): Int =
            (frozen ?: error("尚未 freeze()")).fieldRemap[regIdx]

        /**
         * 定稿全部最终下标：字符串表、类型表，以及 proto_ids/field_ids/method_ids 的段内排序映射。
         * 冻结后禁止再注册符号——ID 表必须按 (返回类型/类/名) 升序写入，排序结果只由符号集合决定，
         * 定稿后再插入符号会让已写进字节码的下标整体错位。
         */
        fun freeze(): Frozen {
            check(frozen == null) { "已经 freeze 过了" }
            val o = DexEncoder.computeOrders(this)
            finalStrings = o.stringIdx
            return Frozen(o.stringIdx, o.typeIdx, o.protoRemap, o.fieldRemap, o.methodRemap)
                .also { frozen = it }
        }

        fun addClass(
            type: String,
            superclass: String,
            staticFields: List<EncodedField> = emptyList(),
            instanceFields: List<EncodedField> = emptyList(),
            directMethods: List<EncodedMethod> = emptyList(),
            virtualMethods: List<EncodedMethod> = emptyList()
        ) {
            val f = frozen ?: error("必须先 freeze() 再 addClass：EncodedField/EncodedMethod 要用最终下标")
            // 新的类描述符会插入 type 表、重排其后的所有下标，因此类名与父类名必须已在表中
            for (t in listOf(type, superclass)) {
                if (t !in f.typeIdx) {
                    error("类名/父类名 $t 必须先作为某个 method/field 的所属类型注册，否则 type 表会整体重排")
                }
            }
            val sortedStatics = staticFields.sortedBy { it.fieldIdx }
            val sortedInstances = instanceFields.sortedBy { it.fieldIdx }
            val sortedDirects = directMethods.sortedBy { it.methodIdx }
            val sortedVirtuals = virtualMethods.sortedBy { it.methodIdx }
            classDefs.add(ClassDef(type, superclass, sortedStatics, sortedInstances, sortedDirects, sortedVirtuals))
        }
    }

    /** [Builder.freeze] 定稿的最终下标表 */
    class Frozen internal constructor(
        internal val stringIdx: Map<String, Int>,
        internal val typeIdx: Map<String, Int>,
        internal val protoRemap: IntArray,
        internal val fieldRemap: IntArray,
        internal val methodRemap: IntArray
    )

    class ClassDef(
        val type: String,
        val superclass: String,
        val staticFields: List<EncodedField>,
        val instanceFields: List<EncodedField>,
        val directMethods: List<EncodedMethod>,
        val virtualMethods: List<EncodedMethod>
    )

    class EncodedField(val fieldIdx: Int, val accessFlags: Int)

    class EncodedMethod(
        val methodIdx: Int,
        val accessFlags: Int,
        val code: ByteArray,
        /** code_item 的 registers_size（含参数寄存器），0 表示取 ins_size */
        val regs: Int = 0
    )

    const val ACC_PUBLIC = 0x1
    const val ACC_PRIVATE = 0x2
    const val ACC_PROTECTED = 0x4
    const val ACC_STATIC = 0x8
    const val ACC_FINAL = 0x10
    const val ACC_NATIVE = 0x100
    const val ACC_CONSTRUCTOR = 0x10000

    /** 计算最终字符串表（必须与 build() 使用的逻辑完全一致） */
    internal fun computeStrings(builder: Builder): List<String> {
        val set = LinkedHashSet<String>()
        builder.protoMap.keys.forEach { key ->
            val parts = key.split("|", limit = 3)
            set.add(parts[0])
            set.add(parts[1])
            if (parts[2].isNotEmpty()) parts[2].split(",").forEach { set.add(it) }
        }
        builder.methodMap.keys.forEach { key ->
            val parts = key.split("|")
            set.add(parts[0])
            set.add(parts[1])
        }
        builder.fieldMap.keys.forEach { key ->
            val parts = key.split("|")
            set.add(parts[0])
            set.add(parts[1])
            set.add(parts[2])
        }
        builder.classDefs.forEach {
            set.add(it.type)
            set.add(it.superclass)
        }
        builder.extraStrings.forEach { set.add(it) }
        set.add("Landroid/app/Application;")
        set.add("Landroid/content/Context;")
        return set.toList().sorted()
    }

    internal class Orders(
        val protos: List<Proto>,
        val methods: List<Method>,
        val fields: List<Field>,
        val strings: List<String>,
        val stringIdx: Map<String, Int>,
        val types: List<String>,
        val typeIdx: Map<String, Int>,
        val protoRemap: IntArray,
        val fieldRemap: IntArray,
        val methodRemap: IntArray
    )

    /**
     * 由注册态符号推导全部最终下标。[Builder.freeze] 与 [build] 必须共用这份逻辑：
     * 两边结果一旦不一致，说明定稿后又注册了符号，写进字节码的下标已整体错位。
     *
     * 校验器要求三个 ID 表段内有序，否则 dexdump 报
     * "Out-of-order proto_id return types" / "Out-of-order field_ids" / "Out-of-order method_ids"：
     *   proto_ids 按 (返回类型, 参数表字典序，短表在前)
     *   field_ids 按 (类, 名, 类型)
     *   method_ids 按 (类, 名, proto)
     */
    internal fun computeOrders(builder: Builder): Orders {
        val protos = builder.protoMap.keys.map { key ->
            val parts = key.split("|", limit = 3)
            Proto(parts[0], parts[1], if (parts[2].isEmpty()) emptyList() else parts[2].split(","))
        }
        val methods = builder.methodMap.keys.map { key ->
            val parts = key.split("|")
            Method(parts[0], parts[1], parts[2].toInt())
        }
        val fields = builder.fieldMap.keys.map { key ->
            val parts = key.split("|")
            Field(parts[0], parts[1], parts[2])
        }

        val strings = computeStrings(builder)
        val stringIdx = HashMap<String, Int>(strings.size).also { m -> strings.forEachIndexed { i, s -> m[s] = i } }
        val types = (protos.map { it.ret } + protos.flatMap { it.params } +
                methods.map { it.defining } + fields.map { it.defining } + fields.map { it.type } +
                builder.classDefs.map { it.type } + builder.classDefs.map { it.superclass })
            .distinct().sorted()
        val typeIdx = HashMap<String, Int>(types.size).also { m -> types.forEachIndexed { i, s -> m[s] = i } }

        fun cmpList(a: List<Int>, b: List<Int>): Int {
            val n = minOf(a.size, b.size)
            for (i in 0 until n) if (a[i] != b[i]) return a[i] - b[i]
            return a.size - b.size
        }
        val protoOrder = protos.indices.sortedWith(Comparator { x, y ->
            val c = typeIdx.getValue(protos[x].ret).compareTo(typeIdx.getValue(protos[y].ret))
            if (c != 0) c
            else cmpList(
                protos[x].params.map { typeIdx.getValue(it) },
                protos[y].params.map { typeIdx.getValue(it) }
            )
        })
        val protoRemap = IntArray(protos.size).also { r ->
            protoOrder.forEachIndexed { newIdx, oldIdx -> r[oldIdx] = newIdx }
        }

        val fieldOrder = fields.indices.sortedWith(Comparator { x, y ->
            val fx = fields[x]; val fy = fields[y]
            var c = typeIdx.getValue(fx.defining).compareTo(typeIdx.getValue(fy.defining))
            if (c == 0) c = stringIdx.getValue(fx.name).compareTo(stringIdx.getValue(fy.name))
            if (c == 0) c = typeIdx.getValue(fx.type).compareTo(typeIdx.getValue(fy.type))
            c
        })
        val fieldRemap = IntArray(fields.size).also { r ->
            fieldOrder.forEachIndexed { newIdx, oldIdx -> r[oldIdx] = newIdx }
        }

        val methodOrder = methods.indices.sortedWith(Comparator { x, y ->
            val mx = methods[x]; val my = methods[y]
            var c = typeIdx.getValue(mx.defining).compareTo(typeIdx.getValue(my.defining))
            if (c == 0) c = stringIdx.getValue(mx.name).compareTo(stringIdx.getValue(my.name))
            if (c == 0) c = protoRemap[mx.protoIdx].compareTo(protoRemap[my.protoIdx])
            c
        })
        val methodRemap = IntArray(methods.size).also { r ->
            methodOrder.forEachIndexed { newIdx, oldIdx -> r[oldIdx] = newIdx }
        }

        return Orders(protos, methods, fields, strings, stringIdx, types, typeIdx, protoRemap, fieldRemap, methodRemap)
    }

    fun build(builder: Builder): ByteArray {
        val o = computeOrders(builder)
        // 字符串下标已按定稿的表写进字节码；若表又变了，const-string 下标会全部错位，必须报错
        builder.finalStrings?.let { resolved ->
            if (resolved != o.stringIdx) {
                val missing = o.stringIdx.keys - resolved.keys
                val gone = resolved.keys - o.stringIdx.keys
                throw IllegalStateException(
                    "字符串表在字节码生成后发生变化，已固化的 const-string 下标会失效：" +
                            "resolved=${resolved.size} current=${o.stringIdx.size} 新增=$missing 消失=$gone"
                )
            }
        }
        builder.finalStrings = o.stringIdx
        val strings = o.strings
        val stringIdx = o.stringIdx
        val types = o.types
        val typeIdx = o.typeIdx

        // ID 表按段内升序写出。remap 是 注册序→表序，写表取其逆：新位置 → 注册序
        fun newToOld(remap: IntArray) =
            IntArray(remap.size).also { a -> remap.forEachIndexed { old, newIdx -> a[newIdx] = old } }

        val protoList = newToOld(o.protoRemap).map { old ->
            val p = o.protos[old]
            ProtoIdx(stringIdx.getValue(p.shorty), typeIdx.getValue(p.ret), p.params.map { q -> typeIdx.getValue(q) })
        }
        val fieldList = newToOld(o.fieldRemap).map { old ->
            val f = o.fields[old]
            FieldIdx(typeIdx.getValue(f.defining), typeIdx.getValue(f.type), stringIdx.getValue(f.name))
        }
        val methodList = newToOld(o.methodRemap).map { old ->
            val m = o.methods[old]
            MethodIdx(typeIdx.getValue(m.defining), o.protoRemap[m.protoIdx], stringIdx.getValue(m.name))
        }

        // class_defs 按 class_idx 升序（DEX 规范要求）；成员下标在 addClass 中已按最终下标升序排好
        val classDefs = builder.classDefs.sortedBy { typeIdx.getValue(it.type) }

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

        /** 把原始指令流包装成完整 code_item（16 字节头 + insns），返回 codeItems 下标 */
        fun codeItemOff(m: EncodedMethod): Int {
            require(m.code.size % 2 == 0) { "insns 长度必须是偶数（code unit）" }
            // EncodedMethod.methodIdx 已是最终下标（addClass 要求 freeze 后才可调用）
            val proto = protoList[methodList[m.methodIdx].protoIdx]
            val isStatic = m.accessFlags and ACC_STATIC != 0
            val ins = proto.paramIdx.size + (if (isStatic) 0 else 1)
            val regs = maxOf(m.regs, ins)
            fun u16(out: ByteArrayOutputStream, v: Int) {
                out.write(v and 0xFF)
                out.write((v shr 8) and 0xFF)
            }
            val item = ByteArrayOutputStream()
            u16(item, regs)                     // registers_size
            u16(item, ins)                      // ins_size
            u16(item, 5)                        // outs_size（35c invoke 最多 5 个参数字）
            u16(item, 0)                        // tries_size
            u16(item, 0); u16(item, 0)          // debug_info_off = 0 (u32)
            u16(item, m.code.size / 2); u16(item, 0) // insns_size (u32)
            item.write(m.code)
            codeItems.add(item.toByteArray())
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

        fun mi(v: Int) = byteArrayOf(
            (v and 0xFF).toByte(), ((v shr 8) and 0xFF).toByte(),
            ((v shr 16) and 0xFF).toByte(), ((v shr 24) and 0xFF).toByte()
        )
        fun ms(v: Int) = byteArrayOf((v and 0xFF).toByte(), ((v shr 8) and 0xFF).toByte())

        // data 段起始偏移只与 ID 表大小有关，先算出来。
        // class_data 中的 code_off 必须写绝对偏移，因此 class_data 要在
        // code_item 之后写——否则 1 字节占位符会被多字节偏移覆盖，破坏后续 uleb 字段。
        val headerSize = 0x70
        var off = headerSize

        val stringIdsOff = off
        off += strings.size * 4

        val typeIdsOff = align(off, 4)
        off = typeIdsOff + types.size * 4

        val protoIdsOff = off
        off += protoList.size * 12

        val fieldIdsOff = align(off, 4)
        off = fieldIdsOff + fieldList.size * 8

        val methodIdsOff = off
        off = methodIdsOff + methodList.size * 8

        val classDefsOff = align(off, 4)
        off = classDefsOff + classDefs.size * 32

        val dataOff = align(off, 4)
        val dataStart = dataOff

        // 先登记 code_item，遍历顺序必须与下面写 class_data 的顺序一致
        val classCodeIdx = Array(classDefs.size) { ci ->
            val cd = classDefs[ci]
            (cd.directMethods + cd.virtualMethods).map { m ->
                if (m.code.isEmpty()) -1 else codeItemOff(m)
            }.toIntArray()
        }

        val typeListOffs = IntArray(typeLists.size)
        for ((i, tl) in typeLists.withIndex()) {
            typeListOffs[i] = align(data.size(), 4)
            padTo(data, typeListOffs[i])
            data.write(byteArrayOf((tl.size and 0xFF).toByte(), ((tl.size shr 8) and 0xFF).toByte(), 0, 0))
            // type_list 的表项是 u16（2 字节），不是 u32
            for (t in tl) data.write(ms(t))
        }

        val codeStart = align(data.size(), 4)
        padTo(data, codeStart)
        for ((i, item) in codeItems.withIndex()) {
            codeOffTable[i] = align(data.size(), 4)
            padTo(data, codeOffTable[i])
            data.write(item)
        }

        // -1 表示该类无 class_data_item；不能用 0 当哨兵，
        // 因为第一个 class_data_item 合法地可能位于 data 段偏移 0
        val classDataOffs = IntArray(classDefs.size) { -1 }
        for ((ci, cd) in classDefs.withIndex()) {
            if (cd.staticFields.isEmpty() && cd.instanceFields.isEmpty() && cd.directMethods.isEmpty() && cd.virtualMethods.isEmpty()) {
                classDataOffs[ci] = -1
                continue
            }
            classDataOffs[ci] = data.size()
            uleb(cd.staticFields.size)
            uleb(cd.instanceFields.size)
            uleb(cd.directMethods.size)
            uleb(cd.virtualMethods.size)
            // 每个列表的 idx_diff 都以 0 为基准（校验器 CheckIntraClassDataItem* 各自从 prev=0 起算），
            // 静态/实例字段之间不能连续累计；成员下标在 addClass 中已按最终下标升序排好
            var prev = 0
            for (f in cd.staticFields) {
                uleb(f.fieldIdx - prev); prev = f.fieldIdx
                uleb(f.accessFlags)
            }
            prev = 0
            for (f in cd.instanceFields) {
                uleb(f.fieldIdx - prev); prev = f.fieldIdx
                uleb(f.accessFlags)
            }
            var codeSeq = 0
            prev = 0
            for (m in cd.directMethods) {
                uleb(m.methodIdx - prev); prev = m.methodIdx
                uleb(m.accessFlags)
                val codeIdx = classCodeIdx[ci][codeSeq++]
                uleb(if (codeIdx < 0) 0 else dataStart + codeOffTable[codeIdx])
            }
            prev = 0
            for (m in cd.virtualMethods) {
                uleb(m.methodIdx - prev); prev = m.methodIdx
                uleb(m.accessFlags)
                val codeIdx = classCodeIdx[ci][codeSeq++]
                uleb(if (codeIdx < 0) 0 else dataStart + codeOffTable[codeIdx])
            }
        }

        // map_list 必须 4 字节对齐，否则 ART/dexdump 拒绝加载 ("should be aligned by 4 for map")
        val mapEntries = mutableListOf<MapItem>()
        fun mapItem(type: Int, size: Int, off: Int) {
            if (size > 0) mapEntries.add(MapItem(type, size, off))
        }

        // string_data_item 必须位于 data 段内（data_off 之后），否则 dexdump 报
        // "Bad offset for data subsection"
        val stringDataRel = align(data.size(), 4)
        padTo(data, stringDataRel)
        val stringDataOffs = IntArray(strings.size)
        for ((i, s) in strings.withIndex()) {
            stringDataOffs[i] = dataStart + data.size()
            // DEX string_data_item = uleb128(utf16_size) + MUTF-8 字节 + 0x00，
            // 没有 utf8 字节长度字段；多写一个 uleb 会让校验器错位，
            // 报 "String longer than indicated size"
            uleb(s.length)
            data.write(mutf8(s))
            data.write(0)
        }

        // map_list 是 data 段最后一项，且必须 4 字节对齐
        val mapListOff = align(data.size(), 4)
        padTo(data, mapListOff)

        // header_item 位于文件偏移 0（不是 map_off）
        mapItem(0x0000, 1, 0)
        mapItem(0x0001, strings.size, stringIdsOff)
        // 字符串数据必须在 map 中登记，否则其所在区段会被校验器当作 padding（"Non-zero padding"）
        if (strings.isNotEmpty()) mapItem(0x2002, strings.size, stringDataOffs[0])
        mapItem(0x0002, types.size, typeIdsOff)
        mapItem(0x0003, protoList.size, protoIdsOff)
        mapItem(0x0004, fieldList.size, fieldIdsOff)
        mapItem(0x0005, methodList.size, methodIdsOff)
        mapItem(0x0006, builder.classDefs.size, classDefsOff)
        mapItem(0x1001, typeLists.size, if (typeLists.isEmpty()) 0 else dataStart + typeListOffs[0])
        // DEX map 类型码：0x2000 = class_data_item（0x1003 是 annotation_set_item，勿混用）
        val classDataCount = classDataOffs.count { it >= 0 }
        if (classDataCount > 0) {
            mapItem(0x2000, classDataCount, dataStart + classDataOffs.first { it >= 0 })
        }
        if (codeItems.isNotEmpty()) {
            mapItem(0x2001, codeItems.size, dataStart + codeStart)
        }
        mapItem(0x1000, 1, dataStart + mapListOff)

        // DEX 校验要求 map 条目按偏移量严格升序（AOSP/dexdump "Out of order map item"）
        val sortedMapEntries = mapEntries.sortedBy { it.off }
        val mapBytes = ByteArrayOutputStream()
        mapBytes.write(mi(sortedMapEntries.size))
        for (e in sortedMapEntries) {
            mapBytes.write(ms(e.type))
            mapBytes.write(ms(0))
            mapBytes.write(mi(e.size))
            mapBytes.write(mi(e.off))
        }

        val dataBytes = data.toByteArray()

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
        for ((ci, cd) in classDefs.withIndex()) {
            out.write(mi(typeIdx.getValue(cd.type)))
            out.write(mi(ACC_PUBLIC))
            out.write(mi(typeIdx.getValue(cd.superclass)))
            out.write(mi(0))
            out.write(mi(0))
            out.write(mi(0))
            out.write(mi(if (classDataOffs[ci] < 0) 0 else dataStart + classDataOffs[ci]))
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

    /**
     * MUTF-8：按 UTF-16 code unit 逐个编码（0x00 → C0 80，代理项各自 3 字节），
     * 保证校验器解出的 code unit 数恰等于 utf16_size。
     * 直接用标准 UTF-8 的话，补充平面字符会被编成 4 字节，校验器直接报非法起始字节。
     */
    private fun mutf8(s: String): ByteArray {
        val out = ByteArrayOutputStream(s.length)
        for (ch in s) {
            val c = ch.code
            when {
                c == 0 -> out.write(byteArrayOf(0xC0.toByte(), 0x80.toByte()))
                c < 0x80 -> out.write(c)
                c < 0x800 -> out.write(
                    byteArrayOf((0xC0 or (c shr 6)).toByte(), (0x80 or (c and 0x3F)).toByte())
                )
                else -> out.write(
                    byteArrayOf(
                        (0xE0 or (c shr 12)).toByte(),
                        (0x80 or ((c shr 6) and 0x3F)).toByte(),
                        (0x80 or (c and 0x3F)).toByte()
                    )
                )
            }
        }
        return out.toByteArray()
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
