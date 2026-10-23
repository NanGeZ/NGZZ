package com.ngzz.protect

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ProtectStageTest {

    @Test
    fun stageOrder() {
        val stages = ProtectStage.all
        assertEquals(5, stages.size)
        assertEquals(ProtectStage.READING, stages[0])
        assertEquals(ProtectStage.CONFIG, stages[1])
        assertEquals(ProtectStage.INJECTING, stages[2])
        assertEquals(ProtectStage.SIGNING, stages[3])
        assertEquals(ProtectStage.DONE, stages[4])
    }

    @Test
    fun fromTitle() {
        assertEquals(ProtectStage.READING, ProtectStage.fromTitle("读取目标 APK"))
        assertEquals(ProtectStage.SIGNING, ProtectStage.fromTitle("重签名与对齐"))
        assertNull(ProtectStage.fromTitle("不存在"))
    }
}
