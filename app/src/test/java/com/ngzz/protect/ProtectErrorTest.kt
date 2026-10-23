package com.ngzz.protect

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProtectErrorTest {

    @Test
    fun errorFromMapsCommonExceptions() {
        val fileNotFound = ProtectError.from(java.io.FileNotFoundException("test"))
        assertTrue(fileNotFound is ProtectError.ApkReadFailed)

        val io = ProtectError.from(java.io.IOException("test"))
        assertTrue(io is ProtectError.OutputWriteFailed)

        val security = ProtectError.from(java.security.GeneralSecurityException("test"))
        assertTrue(security is ProtectError.SigningFailed)

        val zip = ProtectError.from(java.util.zip.ZipException("test"))
        assertTrue(zip is ProtectError.ApkParseFailed)
    }

    @Test
    fun errorFromPreservesProtectError() {
        val original = ProtectError.InjectionFailed()
        val result = ProtectError.from(original)
        assertEquals(original, result)
    }

    @Test
    fun errorFromFallbackToInjection() {
        val unknown = RuntimeException("unknown")
        val result = ProtectError.from(unknown)
        assertTrue(result is ProtectError.InjectionFailed)
    }

    @Test
    fun allStagesHaveTitle() {
        ProtectStage.entries.forEach { stage ->
            assertNotNull(stage.title)
            assertTrue(stage.title.isNotEmpty())
        }
    }

    @Test
    fun progressPercentage() {
        val p = ProtectProgress("test", 2, 5)
        assertEquals(40, p.percentage)

        val complete = ProtectProgress("done", 5, 5, isComplete = true)
        assertEquals(100, complete.percentage)

        val empty = ProtectProgress("test", 0, 0)
        assertEquals(0, empty.percentage)
    }
}
