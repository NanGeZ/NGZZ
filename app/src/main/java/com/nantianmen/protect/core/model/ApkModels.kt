package com.nantianmen.protect.core.model

data class ApkEntry(
    val name: String,
    val compressedSize: Long,
    val size: Long,
    val method: Int,
    val crc: Long
)

data class ManifestInfo(
    val packageName: String,
    val versionName: String,
    val versionCode: Long,
    val minSdk: Int,
    val targetSdk: Int,
    val applicationClass: String,
    val applicationLabel: String,
    val permissions: List<String>,
    val activities: List<String>,
    val services: List<String>,
    val receivers: List<String>,
    val providers: List<String>,
    val exportedActivities: List<String>,
    val debuggable: Boolean,
    val allowBackup: Boolean
)

data class DexInfo(
    val name: String,
    val classCount: Int,
    val methodCount: Int,
    val hasMultiDex: Boolean
)

data class NativeLibInfo(
    val abis: List<String>,
    val libs: List<String>
)

data class ApkAnalysis(
    val fileName: String,
    val fileSize: Long,
    val entries: List<ApkEntry>,
    val manifest: ManifestInfo?,
    val dexInfos: List<DexInfo>,
    val nativeLibs: NativeLibInfo,
    val hasResourcesArsc: Boolean,
    val signatureSchemes: List<String>,
    val warnings: List<String>,
    val parseError: String?
) {
    val dexCount: Int get() = dexInfos.size
    val isMultiDex: Boolean get() = dexInfos.size > 1
}
