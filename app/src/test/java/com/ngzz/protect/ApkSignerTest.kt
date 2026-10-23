package com.ngzz.protect

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class ApkSignerTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    @Test
    fun `signer config default values`() {
        val config = SignerConfig.default()
        assertEquals("ngzz-signer.jks", config.keystorePath)
        assertEquals("ngzz", config.alias)
        assertEquals("ngzz-protect-2026", config.password)
    }

    @Test
    fun `signer config custom values`() {
        val config = SignerConfig(
            keystorePath = "/tmp/test.jks",
            alias = "test",
            password = "pass123",
            dn = "CN=Test"
        )
        assertEquals("/tmp/test.jks", config.keystorePath)
        assertEquals("test", config.alias)
        assertEquals("pass123", config.password)
    }

    @Test
    fun `key material factory creates valid keystore`() {
        val tempFile = File(tempFolder.root, "test.jks")
        val config = SignerConfig(
            keystorePath = tempFile.absolutePath,
            alias = "test",
            password = "test123"
        )

        val material = KeyMaterialFactory.create(config)

        assertEquals("test", material.alias)
        assertNotNull(material.privateKey)
        assertNotNull(material.certificate)
        assertTrue(tempFile.exists())
    }

    @Test
    fun `key material factory loads existing keystore`() {
        val tempFile = File(tempFolder.root, "test.jks")
        val config = SignerConfig(
            keystorePath = tempFile.absolutePath,
            alias = "test",
            password = "test123"
        )

        val material1 = KeyMaterialFactory.create(config)
        val material2 = KeyMaterialFactory.load(config)

        assertEquals(material1.alias, material2.alias)
        assertNotNull(material2.privateKey)
        assertNotNull(material2.certificate)
    }

    @Test
    fun `key material factory ensure creates or loads`() {
        val tempFile = File(tempFolder.root, "test.jks")
        val config = SignerConfig(
            keystorePath = tempFile.absolutePath,
            alias = "test",
            password = "test123"
        )

        val material = KeyMaterialFactory.ensure(config)

        assertEquals("test", material.alias)
        assertNotNull(material.privateKey)
        assertNotNull(material.certificate)
        assertTrue(tempFile.exists())
    }
}
