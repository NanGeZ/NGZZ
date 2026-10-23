package com.ngzz.protect

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class KeyMaterialTest {

    @Test
    fun createGeneratesValidKeystore() {
        val tempFile = File.createTempFile("test-ks", ".jks")
        tempFile.deleteOnExit()
        val config = SignerConfig(
            keystorePath = tempFile.absolutePath,
            alias = "test",
            password = "test123"
        )
        val material = KeyMaterialFactory.create(config)
        assertNotNull(material.privateKey)
        assertNotNull(material.certificate)
        assertEquals("test", material.alias)
    }

    @Test
    fun ensureCreatesAndLoads() {
        val tempFile = File.createTempFile("test-ks2", ".jks")
        tempFile.delete()
        tempFile.deleteOnExit()
        val config = SignerConfig(
            keystorePath = tempFile.absolutePath,
            alias = "ngzz",
            password = "pass"
        )
        val first = KeyMaterialFactory.ensure(config)
        assertTrue(tempFile.exists())
        assertTrue(tempFile.length() > 0)
        val second = KeyMaterialFactory.load(config)
        assertEquals(first.alias, second.alias)
    }
}
