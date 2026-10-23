package com.ngzz.protect

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class IntegrationTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    private fun createTestApk(): File {
        val apk = File(tempFolder.root, "test.apk")
        ZipOutputStream(apk.outputStream()).use { zip ->
            zip.putNextEntry(ZipEntry("AndroidManifest.xml"))
            zip.write("<manifest></manifest>".toByteArray())
            zip.closeEntry()
            zip.putNextEntry(ZipEntry("classes.dex"))
            zip.write(ByteArray(100) { it.toByte() })
            zip.closeEntry()
        }
        return apk
    }

    @Test
    fun `config codec encodes all switches`() {
        val switches = mapOf(
            "sign" to true,
            "anti_debug" to true,
            "package" to true,
            "proxy" to true,
            "root" to true,
            "network" to true,
            "expiry" to true
        )
        val config = ConfigCodec.build(switches)
        assertEquals(ConfigCodec.SIZE, config.size)
        assertArrayEquals(byteArrayOf(0x4E, 0x5A, 0x43, 0x46), config.copyOfRange(0, 4))
        val flags = ConfigCodec.decode(config)
        assertEquals(0x7f, flags)
    }

    @Test
    fun `config codec handles disabled switches`() {
        val switches = mapOf(
            "sign" to true,
            "anti_debug" to false,
            "package" to true,
            "proxy" to true,
            "root" to true,
            "network" to true,
            "expiry" to true
        )
        val config = ConfigCodec.build(switches)
        val flags = ConfigCodec.decode(config)
        assertEquals(0x7f and (1 shl 1).inv(), flags)
    }

    @Test
    fun `config codec roundtrips expiry seconds`() {
        val expiryMillis = 1798761599000L
        val config = ConfigCodec.build(mapOf("expiry" to true), expiryMillis)
        assertEquals(expiryMillis / 1000, ConfigCodec.decodeExpirySeconds(config))
    }

    @Test
    fun `config codec default expiry is zero`() {
        val config = ConfigCodec.build(mapOf("sign" to true))
        assertEquals(0L, ConfigCodec.decodeExpirySeconds(config))
    }

    @Test
    fun `config codec decodes legacy 4 byte format`() {
        val legacy = byteArrayOf(0x7F, 0, 0, 0)
        assertEquals(0x7f, ConfigCodec.decode(legacy))
        assertEquals(0L, ConfigCodec.decodeExpirySeconds(legacy))
    }

    @Test
    fun `protect stage enumeration`() {
        val stages = ProtectStage.values()
        assertEquals(5, stages.size)
        assertEquals(ProtectStage.READING, stages[0])
        assertEquals(ProtectStage.CONFIG, stages[1])
        assertEquals(ProtectStage.INJECTING, stages[2])
        assertEquals(ProtectStage.SIGNING, stages[3])
        assertEquals(ProtectStage.DONE, stages[4])
    }

    @Test
    fun `protect progress calculation`() {
        val progress = ProtectProgress(
            stage = "签名",
            step = 3,
            totalSteps = 5
        )
        assertEquals(60, progress.percentage)
    }

    @Test
    fun `protect error mapping`() {
        val e1 = ProtectError.from(java.io.FileNotFoundException("test"))
        assertTrue(e1 is ProtectError.ApkReadFailed)

        val e2 = ProtectError.from(java.io.IOException("write failed"))
        assertTrue(e2 is ProtectError.OutputWriteFailed)

        val e3 = ProtectError.from(RuntimeException("inject"))
        assertTrue(e3 is ProtectError.InjectionFailed)

        val e4 = ProtectError.from(java.security.GeneralSecurityException("sig"))
        assertTrue(e4 is ProtectError.SigningFailed)
    }

    @Test
    fun `apk io validates file exists`() {
        val apk = createTestApk()
        val names = ApkIo.entryNames(apk.absolutePath)
        assertTrue(names.contains("AndroidManifest.xml"))
        assertTrue(names.contains("classes.dex"))
    }

    @Test
    fun `history record creation`() {
        val record = ProtectRecord(
            apkName = "test.apk",
            outputPath = "test.ngzz.apk",
            success = true
        )
        assertEquals("test.apk", record.apkName)
        assertEquals("test.ngzz.apk", record.outputPath)
        assertTrue(record.success)
    }
}
