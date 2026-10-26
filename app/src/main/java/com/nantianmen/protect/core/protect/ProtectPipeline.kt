package com.nantianmen.protect.core.protect

import com.nantianmen.protect.core.ApkAnalyzer
import com.nantianmen.protect.core.axml.AxmlBuilder
import com.nantianmen.protect.core.axml.AxmlParser
import com.nantianmen.protect.core.axml.XmlElement
import com.nantianmen.protect.core.dex.DexFileReader
import com.nantianmen.protect.core.model.*
import com.nantianmen.protect.core.sign.ApkSigner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

object ProtectPipeline {

    fun protect(
        apkFile: File,
        outputDir: File,
        options: ProtectOptions,
        ownApkPath: String = "",
        onStep: (ProtectProgress) -> Unit
    ): Flow<ProtectResult> = flow {
        val messages = mutableListOf<String>()

        onStep(ProtectProgress(ProtectStep.ANALYZE, "分析 APK", apkFile.name, 0.05f))
        val analysis = ApkAnalyzer.analyze(apkFile)
        messages.add("分析完成: ${analysis.fileName} (${analysis.fileSize / 1024}KB)")
        analysis.warnings.forEach { messages.add("⚠ $it") }
        if (analysis.parseError != null) {
            messages.add("✗ ${analysis.parseError}")
            onStep(ProtectProgress(ProtectStep.ERROR, "分析失败", analysis.parseError!!, 0f))
            emit(ProtectResult(false, null, messages, null))
            return@flow
        }

        val pkg = analysis.manifest?.packageName ?: "unknown"
        messages.add("包名: $pkg")
        messages.add("DEX: ${analysis.dexCount} 个")
        messages.add("原生库: ${analysis.nativeLibs.abis.joinToString(", ").ifEmpty { "无" }}")

        val featureBits = options.features.fold(0) { acc, f ->
            acc or when (f) {
                ProtectFeature.ANTI_DEBUG -> 1
                ProtectFeature.PACKAGE_CHECK -> 2
                ProtectFeature.PACKET_CAPTURE -> 4
                ProtectFeature.ROOT_CHECK -> 8
                ProtectFeature.NETWORK_CHECK -> 16
            }
        }

        onStep(ProtectProgress(ProtectStep.MANIFEST, "修改 Manifest", "注入 GuardApplication", 0.15f))
        val manifestBytes = readEntry(apkFile, "AndroidManifest.xml")
            ?: throw IllegalStateException("无法读取 AndroidManifest.xml")

        val originalApp = ManifestPatcher.resolveOriginalApplication(analysis.manifest, pkg)
        val patchedManifest = patchManifest(manifestBytes, pkg, originalApp)
        messages.add("Manifest 已注入 GuardApplication")

        onStep(ProtectProgress(ProtectStep.DEX, "注入保护代码", "生成 DEX 与原生库", 0.35f))
        val guardSuperApp = resolveGuardSuperclass(apkFile, originalApp)
        if (guardSuperApp != descriptor(originalApp)) {
            messages.add("原 Application 类不可继承，GuardApplication 改为继承 android.app.Application")
        }
        val guardDex = buildGuardDex(featureBits, pkg, guardSuperApp)
        messages.add("保护 DEX 已生成 (${guardDex.size / 1024}KB)")

        val nativeLibs = loadNativeLibs(ownApkPath)
        if (nativeLibs.isEmpty()) {
            val err = "限制: 无法读取内置保护原生库 (libnantianmen_guard.so)，已中止以避免生成无法启动的 APK"
            messages.add("✗ $err")
            onStep(ProtectProgress(ProtectStep.ERROR, "中止", err, 0.35f))
            emit(ProtectResult(false, null, messages, null))
            return@flow
        }
        messages.add("原生库: ${nativeLibs.size} 个 ABI")

        onStep(ProtectProgress(ProtectStep.ASSEMBLE, "重组 APK", "写入修改后的内容", 0.55f))
        val outName = apkFile.nameWithoutExtension + "_protected.apk"
        val outApk = File(outputDir, outName)
        outputDir.mkdirs()

        val tmpApk = File(outputDir, outName + ".tmp")
        rebuildApk(apkFile, tmpApk, patchedManifest, guardDex, nativeLibs, pkg)
        messages.add("APK 重组完成")

        onStep(ProtectProgress(ProtectStep.SIGN, "签名", if (options.autoSign) "自动签名" else "跳过签名", 0.75f))
        if (options.autoSign) {
            val ksFile = File(outputDir, "nantianmen-sign.p12")
            val ksPassword = "nantianmen2024"
            if (!ksFile.exists()) {
                val genResult = ApkSigner.generateKeyStore(ksFile, ksPassword)
                messages.add(genResult.message)
            }
            val keyPair = ApkSigner.loadKey(ksFile, ksPassword)
            if (keyPair != null) {
                val signResult = ApkSigner.signApk(tmpApk, outApk, keyPair.first, keyPair.second)
                messages.add(signResult.message)
                if (!signResult.success) {
                    tmpApk.copyTo(outApk, overwrite = true)
                    messages.add("⚠ 签名失败，输出未签名 APK")
                }
            } else {
                tmpApk.copyTo(outApk, overwrite = true)
                messages.add("⚠ 无法加载签名密钥，输出未签名 APK")
            }
        } else {
            tmpApk.copyTo(outApk, overwrite = true)
            messages.add("已跳过签名（可在设置中开启）")
        }
        tmpApk.delete()

        onStep(ProtectProgress(ProtectStep.VERIFY, "验证", "检查输出 APK", 0.90f))
        val verification = verifyApk(outApk)
        messages.addAll(verification.details)

        onStep(ProtectProgress(ProtectStep.DONE, "完成", outApk.absolutePath, 1.0f))
        emit(ProtectResult(true, outApk.absolutePath, messages, verification))
    }.flowOn(Dispatchers.IO)

    private fun patchManifest(manifestBytes: ByteArray, pkg: String, originalApp: String): ByteArray {
        val root = AxmlParser.parse(manifestBytes)

        val newRoot = XmlElement("manifest")
        copyAttrs(root, newRoot)
        root.children.forEach { child ->
            if (child.name == "application") {
                val newApp = XmlElement("application")
                copyAttrs(child, newApp)
                newApp.attrs.remove("android:name")
                newApp.attr("android:name", ManifestPatcher.GUARD_APPLICATION)
                child.children.forEach { copyNode(it, newApp) }
                newRoot.children.add(newApp)
            } else {
                copyNode(child, newRoot)
            }
        }

        return AxmlBuilder.build(newRoot)
    }

    private fun copyAttrs(source: com.nantianmen.protect.core.axml.AxmlNode, target: XmlElement) {
        source.attributes.forEach { (k, a) ->
            target.rawAttr(k, a.type, a.data, a.value)
        }
    }

    private fun copyNode(source: com.nantianmen.protect.core.axml.AxmlNode, parent: XmlElement) {
        val e = XmlElement(source.name)
        copyAttrs(source, e)
        source.children.forEach { copyNode(it, e) }
        parent.children.add(e)
    }

    private fun descriptor(className: String): String =
        if (className.startsWith("L") && className.endsWith(";")) className
        else "L" + className.replace('.', '/') + ";"

    /**
     * GuardApplication 通过继承原 Application 类来保留其 attachBaseContext/onCreate。
     * 只有原类可继承（public、非 final、非 abstract、非 interface、且确实存在于某个 DEX）时才能这么做，
     * 否则回退为 android.app.Application——此时原类生命周期不再执行，但至少 APK 仍可安装启动。
     */
    private fun resolveGuardSuperclass(apk: File, originalApp: String): String {
        val fallback = GuardDexBuilder.T_APP
        val desc = descriptor(originalApp)
        if (desc == fallback) return fallback
        return try {
            val zip = ZipFile(apk)
            try {
                var flags: Int? = null
                for (e in zip.entries()) {
                    if (e.isDirectory || !e.name.matches(Regex("^classes\\d*\\.dex$"))) continue
                    val dex = DexFileReader.read(zip.getInputStream(e).readBytes())
                    val typeIdx = dex.types.indexOf(desc)
                    if (typeIdx >= 0) {
                        flags = dex.classDefs.firstOrNull { it.classIdx == typeIdx }?.accessFlags
                        break
                    }
                }
                val ACC_PUBLIC = 0x1
                val ACC_FINAL = 0x10
                val ACC_INTERFACE = 0x200
                val ACC_ABSTRACT = 0x400
                val ok = flags != null &&
                        flags and ACC_PUBLIC != 0 &&
                        flags and ACC_FINAL == 0 &&
                        flags and ACC_INTERFACE == 0 &&
                        flags and ACC_ABSTRACT == 0
                if (ok) desc else fallback
            } finally {
                zip.close()
            }
        } catch (_: Throwable) {
            fallback
        }
    }

    private fun buildGuardDex(features: Int, pkg: String, superApp: String): ByteArray {
        return GuardDexBuilder.buildDex(features, pkg, superApp)
    }

    /** 从本应用自身 APK 中读取打包好的保护原生库（Android 上 classloader 资源流不可用于读取 APK 条目） */
    private fun loadNativeLibs(ownApkPath: String): Map<String, ByteArray> {
        val libs = linkedMapOf<String, ByteArray>()
        if (ownApkPath.isBlank()) return libs
        return try {
            val zip = ZipFile(ownApkPath)
            try {
                for (e in zip.entries()) {
                    if (e.isDirectory) continue
                    if (e.name.startsWith("lib/") && e.name.endsWith("/libnantianmen_guard.so")) {
                        libs[e.name] = zip.getInputStream(e).readBytes()
                    }
                }
            } finally {
                zip.close()
            }
            libs
        } catch (_: Throwable) {
            emptyMap()
        }
    }

    private fun rebuildApk(
        input: File,
        output: File,
        manifest: ByteArray,
        guardDex: ByteArray,
        nativeLibs: Map<String, ByteArray>,
        pkg: String
    ) {
        val zip = ZipFile(input)
        // 找出已有 DEX 的最大编号，保护 DEX 追加为下一个 classesN.dex（Android 5+ ART 自动加载全部 classesN.dex）
        var maxDexIndex = 0
        for (e in zip.entries()) {
            val m = Regex("^classes(\\d*)\\.dex$").matchEntire(e.name)
            if (m != null) {
                val n = m.groupValues[1].toIntOrNull() ?: 1
                if (n > maxDexIndex) maxDexIndex = n
            }
        }
        val guardDexName = if (maxDexIndex == 0) "classes.dex" else "classes${maxDexIndex + 1}.dex"

        val zos = ZipOutputStream(output.outputStream().buffered())
        val written = mutableSetOf<String>()

        for (e in zip.entries()) {
            if (e.isDirectory) continue
            when (e.name) {
                "AndroidManifest.xml" -> {
                    val entry = ZipEntry("AndroidManifest.xml")
                    entry.method = ZipEntry.DEFLATED
                    zos.putNextEntry(entry)
                    zos.write(manifest)
                    zos.closeEntry()
                    written.add("AndroidManifest.xml")
                }
                else -> {
                    if (e.name.startsWith("META-INF/") && (
                                e.name.endsWith(".SF") || e.name.endsWith(".RSA") ||
                                        e.name.endsWith(".DSA") || e.name.endsWith(".EC") ||
                                        e.name == "META-INF/MANIFEST.MF"
                                )
                    ) {
                        written.add(e.name)
                        continue
                    }
                    val bytes = zip.getInputStream(e).readBytes()
                    val entry = ZipEntry(e.name)
                    if (e.method == ZipEntry.STORED) {
                        entry.method = ZipEntry.STORED
                        entry.size = bytes.size.toLong()
                        entry.crc = java.util.zip.CRC32().also { it.update(bytes) }.value
                    } else {
                        entry.method = ZipEntry.DEFLATED
                    }
                    zos.putNextEntry(entry)
                    zos.write(bytes)
                    zos.closeEntry()
                    written.add(e.name)
                }
            }
        }

        if (!written.contains(guardDexName)) {
            val entry = ZipEntry(guardDexName)
            entry.method = ZipEntry.STORED
            entry.size = guardDex.size.toLong()
            entry.crc = java.util.zip.CRC32().also { it.update(guardDex) }.value
            zos.putNextEntry(entry)
            zos.write(guardDex)
            zos.closeEntry()
            written.add(guardDexName)
        }

        for ((path, data) in nativeLibs) {
            if (!written.contains(path)) {
                val entry = ZipEntry(path)
                entry.method = ZipEntry.STORED
                entry.size = data.size.toLong()
                entry.crc = java.util.zip.CRC32().also { it.update(data) }.value
                zos.putNextEntry(entry)
                zos.write(data)
                zos.closeEntry()
            }
        }

        zos.close()
        zip.close()
    }

    private fun readEntry(apk: File, name: String): ByteArray? {
        return try {
            val zip = ZipFile(apk)
            val e = zip.getEntry(name)
            val bytes = e?.let { zip.getInputStream(it).readBytes() }
            zip.close()
            bytes
        } catch (_: Throwable) {
            null
        }
    }

    private fun verifyApk(apk: File): VerificationReport {
        val details = mutableListOf<String>()
        var zipOk = false
        var manifestOk = false
        var dexOk = false
        var signatureOk = false

        try {
            val zip = ZipFile(apk)
            zipOk = true
            details.add("✓ ZIP 结构完整")

            val mf = zip.getEntry("AndroidManifest.xml")
            if (mf != null) {
                val bytes = zip.getInputStream(mf).readBytes()
                val root = AxmlParser.parse(bytes)
                manifestOk = root.attr("package")?.isNotEmpty() == true
                details.add(if (manifestOk) "✓ Manifest 有效" else "✗ Manifest 无效")
            }

            val dex = zip.getEntry("classes.dex")
            if (dex != null) {
                val bytes = zip.getInputStream(dex).readBytes()
                dexOk = bytes.size > 4 && bytes[0] == 0x64.toByte() && bytes[1] == 0x65.toByte()
                details.add(if (dexOk) "✓ DEX 有效" else "✗ DEX 无效")
            }

            val hasSig = zip.entries().asSequence().any {
                it.name == "META-INF/MANIFEST.MF" || it.name.endsWith(".RSA") || it.name.endsWith(".SF")
            }
            signatureOk = hasSig
            details.add(if (signatureOk) "✓ 签名块存在" else "✗ 未找到签名")

            zip.close()
        } catch (t: Throwable) {
            details.add("✗ 验证异常: ${t.message}")
        }

        return VerificationReport(zipOk, manifestOk, dexOk, signatureOk, details)
    }
}
