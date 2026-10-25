package com.nantianmen.protect.core.protect

import com.nantianmen.protect.core.dex.DexFileReader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DexEncoderTest {

    @Test
    fun guardDexParses() {
        val dex = GuardDexBuilder.buildDex(31, "com.example.demo", "com.example.demo.App")

        assertEquals('d'.code.toByte(), dex[0])
        assertEquals('e'.code.toByte(), dex[1])
        assertEquals('x'.code.toByte(), dex[2])
        assertEquals(0x0A, dex[3].toInt())

        val parsed = DexFileReader.read(dex)
        assertTrue(parsed.classDefs.isNotEmpty())
        assertTrue(parsed.types.contains("Lcom/nantianmen/guard/GuardApplication;"))
        assertTrue(parsed.types.contains("Lcom/nantianmen/guard/Guard;"))
        assertTrue(parsed.types.contains("Lcom/nantianmen/guard/GuardInit;"))
        assertTrue(parsed.types.contains("Lcom/nantianmen/guard/Bootstrap;"))
        assertTrue(parsed.methods.any { it.nameIdx >= 0 && parsed.strings[it.nameIdx] == "attachBaseContext" })
    }

    @Test
    fun guardDexChecksumIsAdler32OfTail() {
        val dex = GuardDexBuilder.buildDex(31, "com.example.demo", "")
        var a = 1
        var b = 0
        for (i in 12 until dex.size) {
            a = (a + (dex[i].toInt() and 0xFF)) % 65521
            b = (b + a) % 65521
        }
        val expected = (b shl 16) or a
        val actual = ((dex[8].toInt() and 0xFF)) or
                ((dex[9].toInt() and 0xFF) shl 8) or
                ((dex[10].toInt() and 0xFF) shl 16) or
                ((dex[11].toInt() and 0xFF) shl 24)
        assertEquals(expected, actual)
    }
}
