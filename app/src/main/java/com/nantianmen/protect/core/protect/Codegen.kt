package com.nantianmen.protect.core.protect

import java.io.ByteArrayOutputStream

object Codegen {

    fun op(b: Int): ByteArray = byteArrayOf(b.toByte())
    fun op(a: Int, b: Int): ByteArray = byteArrayOf(a.toByte(), b.toByte())
    fun op(a: Int, b: Int, c: Int): ByteArray = byteArrayOf(a.toByte(), b.toByte(), c.toByte())
    fun op(a: Int, b: Int, c: Int, d: Int): ByteArray = byteArrayOf(a.toByte(), b.toByte(), c.toByte(), d.toByte())

    fun codeUnit(v: Int): ByteArray = byteArrayOf((v and 0xFF).toByte(), ((v shr 8) and 0xFF).toByte())

    fun code16(op: Int, a: Int): ByteArray = byteArrayOf(op.toByte(), 0) + codeUnit(a)
    fun code21c(op: Int, a: Int, idx: Int): ByteArray = byteArrayOf(op.toByte(), a.toByte()) + codeUnit(idx)

    /**
     * 35c 格式（3 个 code unit = 6 字节）：
     *   byte0 = op，byte1 = A(参数个数) | G(第 5 个寄存器)
     *   byte2..3 = 索引（小端），byte4 = D|C，byte5 = F|E
     * 实测自 dexdump：`7053 b8fc 4012` → {v0, v4, v2, v1, v3}
     */
    fun code35c(op: Int, argc: Int, idx: Int, regs: List<Int>): ByteArray {
        fun r(i: Int): Int = (regs.getOrElse(i) { 0 } and 0x0F)
        val out = ByteArrayOutputStream()
        out.write(byteArrayOf(op.toByte(), ((argc and 0x0F) shl 4 or r(4)).toByte()))
        out.write(codeUnit(idx))
        out.write(byteArrayOf(((r(1) shl 4) or r(0)).toByte()))
        out.write(byteArrayOf(((r(3) shl 4) or r(2)).toByte()))
        return out.toByteArray()
    }

    fun code31i(op: Int, a: Int, value: Int): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(byteArrayOf(op.toByte(), a.toByte()))
        out.write(codeUnit(value and 0xFFFF))
        out.write(codeUnit((value ushr 16) and 0xFFFF))
        return out.toByteArray()
    }

    fun code21s(op: Int, a: Int, value: Int): ByteArray = op(op, a) + codeUnit(value)
    fun code21h(op: Int, a: Int, value: Int): ByteArray = op(op, a) + codeUnit(value)
    fun code11x(op: Int, a: Int): ByteArray = op(op, a)
    fun code10x(op: Int): ByteArray = op(op, 0)
    fun code21t(op: Int, a: Int, offset: Int): ByteArray = op(op, a) + codeUnit(offset)
    fun code20t(op: Int, offset: Int): ByteArray = op(op, 0) + codeUnit(offset)
    fun code31c(op: Int, a: Int, idx: Int): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(byteArrayOf(op.toByte(), a.toByte()))
        out.write(codeUnit(idx))
        out.write(codeUnit(0))
        return out.toByteArray()
    }

    /** 22c/22t 格式（2 个 code unit = 4 字节）：byte1 = B|A，byte2..3 = 索引/偏移 */
    fun code22c(op: Int, a: Int, b: Int, idx: Int): ByteArray =
        byteArrayOf(op.toByte(), ((b and 0x0F) shl 4 or (a and 0x0F)).toByte()) + codeUnit(idx)

    fun code22t(op: Int, a: Int, b: Int, offset: Int): ByteArray =
        byteArrayOf(op.toByte(), ((b and 0x0F) shl 4 or (a and 0x0F)).toByte()) + codeUnit(offset)

    fun code22x(op: Int, a: Int, b: Int): ByteArray = op(op, a) + codeUnit(b)

    fun wrapCatchAll(handler: Int): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(byteArrayOf(0x00, 0x00))
        out.write(codeUnit(handler))
        return out.toByteArray()
    }

    // 静态字段 opcode：60 sget / 61 sget-wide / 62 sget-object / 63 sget-boolean
    //                 67 sput  / 68 sput-wide  / 69 sput-object  / 6a sput-boolean
    fun staticFieldCode(f: Int): ByteArray = code21c(0x60, 0, f)
    fun sget(f: Int): ByteArray = code21c(0x60, 0, f)
    fun sput(f: Int): ByteArray = code21c(0x67, 0, f)
    fun sgetTo(reg: Int, f: Int): ByteArray = code21c(0x60, reg, f)
    fun sputFrom(reg: Int, f: Int): ByteArray = code21c(0x67, reg, f)
    fun sgetInt(reg: Int, f: Int): ByteArray = code21c(0x60, reg, f)
    fun sputInt(reg: Int, f: Int): ByteArray = code21c(0x67, reg, f)
    fun sgetObject(reg: Int, f: Int): ByteArray = code21c(0x62, reg, f)
    fun sputObject(reg: Int, f: Int): ByteArray = code21c(0x69, reg, f)
    fun sgetBool(reg: Int, f: Int): ByteArray = code21c(0x63, reg, f)
    fun sputBool(reg: Int, f: Int): ByteArray = code21c(0x6a, reg, f)

    /** 12x 格式（1 个 code unit）：byte1 = B|A */
    fun move(a: Int, b: Int): ByteArray = byteArrayOf(0x01, ((b and 0x0F) shl 4 or (a and 0x0F)).toByte())

    fun invokeStatic(m: Int, regs: List<Int>): ByteArray = code35c(0x71, regs.size, m, regs)
    fun invokeStaticRange(m: Int, argc: Int, firstReg: Int): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(byteArrayOf(0x77.toByte(), argc.toByte()))
        out.write(codeUnit(m))
        out.write(codeUnit(firstReg))
        return out.toByteArray()
    }

    fun invokeVirtual(m: Int, regs: List<Int>): ByteArray = code35c(0x6e, regs.size, m, regs)
    fun invokeDirect(m: Int, regs: List<Int>): ByteArray = code35c(0x70, regs.size, m, regs)
    fun invokeSuper(m: Int, regs: List<Int>): ByteArray = code35c(0x6f, regs.size, m, regs)
    fun invokeInterface(m: Int, regs: List<Int>): ByteArray = code35c(0x72, regs.size, m, regs)

    fun constString(a: Int, idx: Int): ByteArray = code21c(0x1a, a, idx)
    fun const4(a: Int, v: Int): ByteArray = op(0x12, ((v and 0x0F) shl 4) or (a and 0x0F))
    fun const16(a: Int, v: Int): ByteArray = code21s(0x13, a, v)
    fun const(a: Int, v: Int): ByteArray = code31i(0x14, a, v)

    fun moveResult(a: Int): ByteArray = code11x(0x0a, a)
    fun moveResultObject(a: Int): ByteArray = code11x(0x0c, a)
    fun moveResultWide(a: Int): ByteArray = code11x(0x0b, a)
    fun returnVoid(): ByteArray = code10x(0x0e)
    fun returnObject(a: Int): ByteArray = code11x(0x11, a)
    fun returnVoid0(): ByteArray = code10x(0x0f)

    fun checkCast(a: Int, idx: Int): ByteArray = code21c(0x1f, a, idx)
    fun instanceOf(a: Int, b: Int, idx: Int): ByteArray = code22c(0x20, a, b, idx)
    fun newArray(a: Int, b: Int, idx: Int): ByteArray = code22c(0x23, a, b, idx)
    fun filledNewArray(idx: Int, regs: List<Int>): ByteArray = code35c(0x24, regs.size, idx, regs)

    fun aget(a: Int, b: Int, c: Int): ByteArray = op(0x44, a, b, c)
    fun aput(a: Int, b: Int, c: Int): ByteArray = op(0x4b, a, b, c)
    fun iget(a: Int, b: Int, idx: Int): ByteArray = code22c(0x52, a, b, idx)
    fun iput(a: Int, b: Int, idx: Int): ByteArray = code22c(0x59, a, b, idx)

    /** 22b 格式（2 个 code unit）：byte1 = B|A，byte2 = 有符号 8 位字面量，byte3 = 0 */
    fun code22b(op: Int, a: Int, b: Int, value: Int): ByteArray =
        byteArrayOf(
            op.toByte(),
            ((b and 0x0F) shl 4 or (a and 0x0F)).toByte(),
            (value and 0xFF).toByte(),
            0
        )

    fun addIntLit8(a: Int, b: Int, v: Int): ByteArray = code22b(0xd8, a, b, v)
    fun addIntLit8_16(a: Int, b: Int, v: Int): ByteArray = code22b(0xd8, a, b, v)

    fun ifEqz(a: Int, offset: Int): ByteArray = code21t(0x38, a, offset)
    fun ifNez(a: Int, offset: Int): ByteArray = code21t(0x39, a, offset)
    fun ifEq(a: Int, b: Int, offset: Int): ByteArray = code22t(0x32, a, b, offset)
    fun ifNe(a: Int, b: Int, offset: Int): ByteArray = code22t(0x33, a, b, offset)

    fun newInst(a: Int, idx: Int): ByteArray = code21c(0x22, a, idx)
    fun throwEx(a: Int): ByteArray = code11x(0x27, a)

    fun codeOf(vararg parts: ByteArray): ByteArray {
        val out = ByteArrayOutputStream()
        parts.forEach { out.write(it) }
        return out.toByteArray()
    }
}
