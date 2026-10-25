package com.nantianmen.protect.core.sign

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 用 Android SDK 的 apksigner 官方工具验证本项目签名器输出的 v1+v2 签名。
 * 环境中没有 apksigner 时自动跳过。
 */
class ApkSignerVerifyTest {

    @Test
    fun signedZipPassesOfficialApksigner() {
        val buildTools = File("/opt/android-sdk/build-tools")
        val apksigner = buildTools.listFiles()
            ?.map { File(it, "apksigner") }
            ?.firstOrNull { it.exists() }
        assumeTrue("apksigner CLI 不在环境中，跳过", apksigner != null)

        val tmpDir = Files.createTempDirectory("ntmsign").toFile()
        try {
            val input = File(tmpDir, "in.apk")
            ZipOutputStream(input.outputStream()).use { zos ->
                fun entry(name: String, bytes: ByteArray, stored: Boolean = false) {
                    val e = ZipEntry(name)
                    if (stored) {
                        e.method = ZipEntry.STORED
                        e.size = bytes.size.toLong()
                        e.crc = java.util.zip.CRC32().also { it.update(bytes) }.value
                    }
                    zos.putNextEntry(e)
                    zos.write(bytes)
                    zos.closeEntry()
                }
                val manifest = com.nantianmen.protect.core.axml.XmlElement("manifest")
                manifest.attr("package", "com.example.sigtest")
                manifest.rawAttr("android:minSdkVersion", 0x10, 26, "26")
                manifest.rawAttr("android:targetSdkVersion", 0x10, 35, "35")
                // apksigner 通过 uses-sdk 确定 minSdk（SHA-256 v1 签名需 API 18+）
                manifest.child("uses-sdk") { rawAttr("android:minSdkVersion", 0x10, 26, "26") }
                manifest.child("application") { attr("android:label", "SigTest") }
                entry(
                    "AndroidManifest.xml",
                    com.nantianmen.protect.core.axml.AxmlBuilder.build(manifest)
                )
                entry("classes.dex", "dex\n035-fake".toByteArray())
                entry("res/raw/small.bin", byteArrayOf(1, 2, 3), stored = true)
                // 超过 1MB，覆盖 v2 摘要的多块切分
                entry("assets/big.bin", ByteArray(2 * 1024 * 1024 + 17) { (it * 31).toByte() })
            }

            val ks = File(tmpDir, "ks.p12")
            assertTrue(ApkSigner.generateKeyStore(ks, "pw123456").success)
            val keyPair = ApkSigner.loadKey(ks, "pw123456")!!
            val output = File(tmpDir, "out.apk")

            val result = ApkSigner.signApk(input, output, keyPair.first, keyPair.second)
            assertTrue("signApk 失败: ${result.message}", result.success)
            assertTrue(output.exists() && output.length() > 0)

            val proc = ProcessBuilder(
                apksigner!!.absolutePath, "verify", "--verbose", output.absolutePath
            ).redirectErrorStream(true).start()
            val out = proc.inputStream.bufferedReader().readText()
            val rc = proc.waitFor()
            assertEquals("apksigner 输出:\n$out", 0, rc)
            assertTrue("v2 未验证通过:\n$out", out.contains("v2") && out.contains("Verified"))
        } finally {
            tmpDir.deleteRecursively()
        }
    }
}
