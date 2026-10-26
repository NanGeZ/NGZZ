package com.nantianmen.protect.core.protect

import com.nantianmen.protect.core.model.ProtectFeature
import com.nantianmen.protect.core.model.ProtectOptions
import com.nantianmen.protect.core.model.ProtectResult
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.io.File
import java.nio.file.Files

/**
 * 真实样品 APK 保护测试：对用户提供的 demo_1.0.apk 走完整保护流水线。
 * 样品或自身 APK 缺失时自动跳过。
 */
class DemoSampleTest {

    @Test
    fun protectRealDemoSample() = runBlocking {
        val sample = File("/workspace/.uploads/b5e0591a-8293-4c04-a8be-b7cf92245fed_demo_1.0.apk")
        assumeTrue("样品 APK 不存在，跳过", sample.exists())
        val own = File("/workspace/app/build/outputs/apk/release/app-release.apk")
        assumeTrue("本体 APK 未构建，跳过", own.exists())

        val outDir = Files.createTempDirectory("ntmdemo").toFile()
        try {
            val options = ProtectOptions(
                features = setOf(
                    ProtectFeature.ANTI_DEBUG, ProtectFeature.PACKAGE_CHECK,
                    ProtectFeature.PACKET_CAPTURE, ProtectFeature.ROOT_CHECK,
                    ProtectFeature.NETWORK_CHECK
                ),
                autoSign = true
            )
            var result: ProtectResult? = null
            ProtectPipeline.protect(sample, outDir, options, own.absolutePath) { p ->
                println("STEP ${p.step}: ${p.message}")
            }.collect { result = it }

            val r = result!!
            r.messages.forEach { println("MSG $it") }
            println("OUTPUT: ${r.outputApkPath}")
            // 便于 shell 后续用 aapt2/apksigner 深入校验
            val fixed = File("/tmp/demo_protected.apk")
            File(r.outputApkPath!!).copyTo(fixed, overwrite = true)
            println("COPIED $fixed ${fixed.length()}")
        } finally {
            outDir.deleteRecursively()
        }
    }
}
