package com.ngzz.protect

import org.junit.Assert.assertEquals
import org.junit.Test

class ProtectionConfigTest {

    @Test
    fun flagsEncodeSwitchBits() {
        val switches = Protection.entries.associate { it.id to true }
        val config = ConfigCodec.build(switches)
        assertEquals(4, config.size)
        assertEquals(0x7f, ConfigCodec.decode(config))
    }

    @Test
    fun disabledSwitchClearsBit() {
        val switches = Protection.entries.associate { it.id to true } +
            (Protection.ROOT.id to false)
        val config = ConfigCodec.build(switches)
        assertEquals(0x6f, ConfigCodec.decode(config))
    }
}
