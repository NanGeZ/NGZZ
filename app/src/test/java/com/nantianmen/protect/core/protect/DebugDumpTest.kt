package com.nantianmen.protect.core.protect

import org.junit.Test

class DebugDumpTest {
    @Test
    fun dump() {
        val dex = GuardDexBuilder.buildDex(31, "com.example.demo", "com.example.demo.App")
        fun u32(off: Int): Int =
            (dex[off].toInt() and 0xFF) or
                    ((dex[off + 1].toInt() and 0xFF) shl 8) or
                    ((dex[off + 2].toInt() and 0xFF) shl 16) or
                    ((dex[off + 3].toInt() and 0xFF) shl 24)
        val fileSize = u32(32)
        val headerSize = u32(36)
        val stringIdsSize = u32(56)
        val stringIdsOff = u32(60)
        val typeIdsSize = u32(64)
        val typeIdsOff = u32(68)
        val protoIdsSize = u32(72)
        val protoIdsOff = u32(76)
        val fieldIdsSize = u32(80)
        val fieldIdsOff = u32(84)
        val methodIdsSize = u32(88)
        val methodIdsOff = u32(92)
        val classDefsSize = u32(96)
        val classDefsOff = u32(100)
        val dataSize = u32(104)
        val dataOff = u32(108)
        println("dex.size=${dex.size} fileSize=$fileSize headerSize=$headerSize")
        println("strings: size=$stringIdsSize off=$stringIdsOff")
        println("types:   size=$typeIdsSize off=$typeIdsOff")
        println("protos:  size=$protoIdsSize off=$protoIdsOff")
        println("fields:  size=$fieldIdsSize off=$fieldIdsOff")
        println("methods: size=$methodIdsSize off=$methodIdsOff")
        println("classes: size=$classDefsSize off=$classDefsOff")
        println("data:    size=$dataSize off=$dataOff")
        for (i in 0 until protoIdsSize) {
            val base = protoIdsOff + i * 12
            val shorty = u32(base)
            val ret = u32(base + 4)
            val paramsOff = u32(base + 8)
            println("proto[$i] shortyIdx=$shorty retIdx=$ret paramsOff=$paramsOff (0x${Integer.toHexString(paramsOff)})")
        }
    }
}
