package com.ngzz.protect

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class ApkSignerTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    private fun testConfig(file: File, alias: String = "test", pass: String = "test123") =
        SignerConfig(
            keystorePath = file.absolutePath,
            alias = alias,
            storePassword = pass,
            keyPassword = pass
        )

    @Test
    fun `signer config splits store and key password`() {
        val config = SignerConfig(
            keystorePath = "/tmp/test.jks",
            alias = "test",
            storePassword = "store1",
            keyPassword = "key1"
        )
        assertEquals("store1", config.storePassword)
        assertEquals("key1", config.keyPassword)
        assertEquals("store1", SignerConfig("/tmp/a.jks", "t", "store1").keyPassword)
    }

    @Test
    fun `generate then load roundtrip`() {
        val tempFile = File(tempFolder.root, "test.jks")
        val config = testConfig(tempFile)

        val generated = KeyMaterialFactory.generate(config)
        assertEquals("test", generated.alias)
        assertNotNull(generated.privateKey)
        assertNotNull(generated.certificate)
        assertTrue(tempFile.exists())

        val loaded = KeyMaterialFactory.load(config)
        assertEquals(generated.alias, loaded.alias)
        assertNotNull(loaded.privateKey)
        assertNotNull(loaded.certificate)
    }

    @Test
    fun `load missing keystore throws with clear message`() {
        val missing = File(tempFolder.root, "missing.jks")
        try {
            KeyMaterialFactory.load(testConfig(missing))
            fail("expected ProtectError")
        } catch (e: ProtectError.KeystoreFailed) {
            assertTrue(e.message!!.contains("不存在"))
        }
    }

    @Test
    fun `load wrong password throws KeystoreFailed`() {
        val tempFile = File(tempFolder.root, "test.jks")
        KeyMaterialFactory.generate(testConfig(tempFile))
        try {
            KeyMaterialFactory.load(testConfig(tempFile, pass = "wrong"))
            fail("expected ProtectError")
        } catch (e: ProtectError.KeystoreFailed) {
            assertTrue(e.message!!.contains("无法读取签名文件"))
        }
    }

    @Test
    fun `load wrong alias throws KeystoreFailed`() {
        val tempFile = File(tempFolder.root, "test.jks")
        KeyMaterialFactory.generate(testConfig(tempFile))
        try {
            KeyMaterialFactory.load(testConfig(tempFile, alias = "nope"))
            fail("expected ProtectError")
        } catch (e: ProtectError.KeystoreFailed) {
            assertTrue(e.message!!.contains("别名"))
        }
    }

    @Test
    fun `load from stream tries both store types`() {
        val tempFile = File(tempFolder.root, "test.jks")
        val config = testConfig(tempFile)
        KeyMaterialFactory.generate(config, type = "JKS")
        tempFile.inputStream().use { stream ->
            val material = KeyMaterialFactory.load(stream, config)
            assertEquals("test", material.alias)
        }
    }

    @Test
    fun `sign produces verifiable apk`() {
        val keystore = File(tempFolder.root, "test.jks")
        KeyMaterialFactory.generate(testConfig(keystore))

        val unsigned = File(tempFolder.root, "unsigned.apk")
        java.util.zip.ZipOutputStream(unsigned.outputStream()).use { zip ->
            zip.putNextEntry(java.util.zip.ZipEntry("AndroidManifest.xml"))
            zip.write("<manifest package=\"com.example.test\"/>".toByteArray())
            zip.closeEntry()
        }

        val signed = File(tempFolder.root, "signed.apk")
        ApkSignerWrapper(testConfig(keystore)).sign(unsigned.absolutePath, signed.absolutePath)

        assertTrue(signed.exists())
        assertTrue(signed.length() > 0)
        val names = ApkIo.entryNames(signed.absolutePath)
        assertTrue(names.any { it.startsWith("META-INF/") && it.endsWith(".RSA") } ||
            names.any { it.startsWith("META-INF/") && it.endsWith(".SF") })
    }
}
