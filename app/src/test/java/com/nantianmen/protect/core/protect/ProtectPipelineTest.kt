package com.nantianmen.protect.core.protect

import com.nantianmen.protect.core.axml.AxmlBuilder
import com.nantianmen.protect.core.axml.XmlElement
import com.nantianmen.protect.core.model.ProtectFeature
import com.nantianmen.protect.core.model.ProtectOptions
import com.nantianmen.protect.core.model.ProtectStep
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

/**
 * 端到端流水线测试：合成目标 APK -> 保护重组 -> 自动签名 -> 官方 apksigner 校验输出。
 */
class ProtectPipelineTest {

    @Test
    fun protectProducesSignedApkWithGuardDex() = runBlocking {
        val tmp = Files.createTempDirectory("ntmpipe").toFile()
        try {
            // 目标 APK：二进制 Manifest + 假 DEX + STORED 资源
            val target = File(tmp, "target.apk")
            ZipOutputStream(target.outputStream()).use { zos ->
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
                val manifest = XmlElement("manifest")
                manifest.attr("package", "com.example.target")
                manifest.rawAttr("android:minSdkVersion", 0x10, 26, "26")
                manifest.rawAttr("android:targetSdkVersion", 0x10, 35, "35")
                manifest.child("uses-sdk") { rawAttr("android:minSdkVersion", 0x10, 26, "26") }
                manifest.child("application") {
                    attr("android:name", "com.example.target.App")
                    attr("android:label", "Target")
                    child("activity") { attr("android:name", ".MainActivity") }
                }
                entry("AndroidManifest.xml", AxmlBuilder.build(manifest))
                entry("classes.dex", ByteArray(0x70) { if (it < 4) "dex\n".toByteArray()[it] else 0 })
                entry("res/raw/data.bin", byteArrayOf(9, 9, 9), stored = true)
            }

            // 本应用自身 APK：仅包含保护原生库
            val ownApk = File(tmp, "own.apk")
            ZipOutputStream(ownApk.outputStream()).use { zos ->
                val e = ZipEntry("lib/arm64-v8a/libnantianmen_guard.so")
                e.method = ZipEntry.STORED
                val so = byteArrayOf(0x7f, 45, 76, 70) + ByteArray(64)
                e.size = so.size.toLong()
                e.crc = java.util.zip.CRC32().also { it.update(so) }.value
                zos.putNextEntry(e)
                zos.write(so)
                zos.closeEntry()
            }

            val outDir = File(tmp, "out")
            val options = ProtectOptions(
                features = setOf(
                    ProtectFeature.ANTI_DEBUG, ProtectFeature.PACKAGE_CHECK,
                    ProtectFeature.PACKET_CAPTURE, ProtectFeature.ROOT_CHECK,
                    ProtectFeature.NETWORK_CHECK
                ),
                autoSign = true
            )

            var lastStep = ProtectStep.IDLE
            val result = ProtectPipeline.protect(target, outDir, options, ownApk.absolutePath) {
                lastStep = it.step
            }.let { flow ->
                var r: com.nantianmen.protect.core.model.ProtectResult? = null
                flow.collect { r = it }
                r!!
            }

            assertTrue("保护失败: ${result.messages}", result.success)
            assertEquals(ProtectStep.DONE, lastStep)

            val outApk = File(result.outputApkPath!!)
            assertTrue(outApk.exists() && outApk.length() > 0)

            val zip = ZipFile(outApk)
            try {
                // 保护 DEX 已追加
                val dexNames = zip.entries().asSequence().map { it.name }
                    .filter { Regex("^classes\\d*\\.dex$").matches(it) }.toList()
                val allNames = zip.entries().asSequence().map { it.name }.toList()
                assertTrue("缺少保护 DEX: $dexNames / all=$allNames / ${result.messages}", dexNames.size >= 2)

                // Manifest 已替换 Application
                val manifestBytes = zip.getInputStream(zip.getEntry("AndroidManifest.xml")).readBytes()
                val root = com.nantianmen.protect.core.axml.AxmlParser.parse(manifestBytes)
                val app = root.children.first { it.name == "application" }
                assertEquals(
                    com.nantianmen.protect.core.protect.ManifestPatcher.GUARD_APPLICATION,
                    app.attr("android:name")
                )

                // 原生库已注入，v1 签名条目由本流水线新写入
                assertTrue(zip.getEntry("lib/arm64-v8a/libnantianmen_guard.so") != null)
                assertTrue(
                    "缺少新签名条目",
                    zip.getEntry("META-INF/MANIFEST.MF") != null &&
                            zip.getEntry("META-INF/CERT.SF") != null &&
                            zip.getEntry("META-INF/CERT.RSA") != null
                )
            } finally {
                zip.close()
            }

            // 官方 apksigner 校验
            val apksigner = File("/opt/android-sdk/build-tools").listFiles()
                ?.map { File(it, "apksigner") }?.firstOrNull { it.exists() }
            if (apksigner != null) {
                val proc = ProcessBuilder(
                    apksigner.absolutePath, "verify", "--verbose", outApk.absolutePath
                ).redirectErrorStream(true).start()
                val out = proc.inputStream.bufferedReader().readText()
                assertEquals("apksigner 输出:\n$out", 0, proc.waitFor())
                assertTrue("v2 未通过:\n$out", out.contains("v2") && out.contains("Verified"))
            }

            assertTrue("验证报告缺失", result.verification != null && result.verification!!.zipOk)
        } finally {
            tmp.deleteRecursively()
        }
    }
}
