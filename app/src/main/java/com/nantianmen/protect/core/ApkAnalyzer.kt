package com.nantianmen.protect.core

import com.nantianmen.protect.core.axml.AxmlParser
import com.nantianmen.protect.core.dex.DexParser
import com.nantianmen.protect.core.model.ApkAnalysis
import com.nantianmen.protect.core.model.ApkEntry
import com.nantianmen.protect.core.model.DexInfo
import com.nantianmen.protect.core.model.ManifestInfo
import com.nantianmen.protect.core.model.NativeLibInfo
import java.io.File
import java.util.zip.ZipFile

object ApkAnalyzer {

    fun analyze(file: File): ApkAnalysis {
        val warnings = mutableListOf<String>()
        return try {
            val zip = ZipFile(file)
            val entries = mutableListOf<ApkEntry>()
            var manifestBytes: ByteArray? = null
            val dexInfos = mutableListOf<DexInfo>()
            val libs = mutableListOf<String>()
            val abis = mutableSetOf<String>()
            var hasResourcesArsc = false
            var hasV2 = false
            var hasV3 = false
            var hasV1 = false

            zip.entries().asSequence().forEach { e ->
                entries.add(
                    ApkEntry(e.name, e.compressedSize, e.size, e.method, e.crc)
                )
                when {
                    e.name == "AndroidManifest.xml" -> manifestBytes = zip.getInputStream(e).readBytes()
                    e.name.matches(Regex("classes\\d*\\.dex")) -> {
                        val bytes = zip.getInputStream(e).readBytes()
                        dexInfos.add(DexParser.parse(bytes, e.name))
                    }
                    e.name == "resources.arsc" -> hasResourcesArsc = true
                    e.name.startsWith("lib/") && e.name.endsWith(".so") -> {
                        libs.add(e.name)
                        e.name.split("/").getOrNull(1)?.let { abis.add(it) }
                    }
                    e.name == "META-INF/MANIFEST.MF" -> hasV1 = true
                    e.name.startsWith("META-INF/") && e.name.endsWith(".SF") -> hasV1 = true
                    e.name == "META-INF/com/android/build/gradle/app-metadata.properties" -> {}
                    e.name.contains("stamp-cert-sha256") -> hasV3 = true
                }
            }

            val manifest = manifestBytes?.let { parseManifest(it, warnings) }

            val schemes = mutableListOf<String>()
            if (hasV1) schemes.add("v1 (JAR)")
            if (hasV2) schemes.add("v2 (APK Sig Block)")
            if (hasV3) schemes.add("v3")

            if (manifest == null) warnings.add("无法解析 AndroidManifest.xml")
            if (dexInfos.isEmpty()) warnings.add("未找到 DEX 文件")
            if (manifest?.debuggable == true) warnings.add("目标 APK 开启了 debuggable")
            if (manifest?.allowBackup == true) warnings.add("目标 APK 允许备份 (allowBackup=true)")

            zip.close()

            ApkAnalysis(
                fileName = file.name,
                fileSize = file.length(),
                entries = entries,
                manifest = manifest,
                dexInfos = dexInfos,
                nativeLibs = NativeLibInfo(abis.toList(), libs),
                hasResourcesArsc = hasResourcesArsc,
                signatureSchemes = schemes,
                warnings = warnings,
                parseError = null
            )
        } catch (t: Throwable) {
            ApkAnalysis(
                fileName = file.name,
                fileSize = file.length(),
                entries = emptyList(),
                manifest = null,
                dexInfos = emptyList(),
                nativeLibs = NativeLibInfo(emptyList(), emptyList()),
                hasResourcesArsc = false,
                signatureSchemes = emptyList(),
                warnings = warnings,
                parseError = "解析失败: ${t.message}"
            )
        }
    }

    private fun parseManifest(bytes: ByteArray, warnings: MutableList<String>): ManifestInfo? {
        return try {
            val root = AxmlParser.parse(bytes)
            val app = root.find("application")
            val usesSdk = root.find("uses-sdk")

            fun List<String>.toClassNames(pkg: String): List<String> = this.mapNotNull { name ->
                when {
                    name.startsWith(".") -> pkg + name
                    name.contains(".") -> name
                    name.isEmpty() -> null
                    else -> "$pkg.$name"
                }
            }

            val pkg = root.attr("package") ?: ""
            val activities = root.findAll("activity").mapNotNull { it.attr("android:name") }.toClassNames(pkg)
            val services = root.findAll("service").mapNotNull { it.attr("android:name") }.toClassNames(pkg)
            val receivers = root.findAll("receiver").mapNotNull { it.attr("android:name") }.toClassNames(pkg)
            val providers = root.findAll("provider").mapNotNull { it.attr("android:name") }.toClassNames(pkg)
            val exported = root.findAll("activity")
                .filter { it.attr("android:exported") == "true" }
                .mapNotNull { it.attr("android:name") }.toClassNames(pkg)

            ManifestInfo(
                packageName = pkg,
                versionName = root.attr("android:versionName") ?: "",
                versionCode = root.attr("android:versionCode")?.toLongOrNull() ?: 0,
                minSdk = usesSdk?.attr("android:minSdkVersion")?.toIntOrNull() ?: 0,
                targetSdk = usesSdk?.attr("android:targetSdkVersion")?.toIntOrNull() ?: 0,
                applicationClass = app?.attr("android:name")?.let {
                    when {
                        it.startsWith(".") -> pkg + it
                        it.contains(".") -> it
                        else -> "$pkg.$it"
                    }
                } ?: "",
                applicationLabel = app?.attr("android:label") ?: "",
                permissions = root.findAll("uses-permission").mapNotNull { it.attr("android:name") },
                activities = activities,
                services = services,
                receivers = receivers,
                providers = providers,
                exportedActivities = exported,
                debuggable = app?.attr("android:debuggable") == "true",
                allowBackup = app?.attr("android:allowBackup") != "false"
            )
        } catch (t: Throwable) {
            warnings.add("Manifest 解析异常: ${t.message}")
            null
        }
    }
}
