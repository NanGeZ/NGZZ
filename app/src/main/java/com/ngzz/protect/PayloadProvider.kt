package com.ngzz.protect

import android.content.Context

class PayloadProvider(private val context: Context) {

    fun extractAll(abis: List<String>): Map<String, ByteArray> {
        return abis.associateWith { abi ->
            nativeLibPath(abi).readBytes()
        }
    }

    fun nativeLibPath(abi: String): java.io.File {
        val out = java.io.File(context.filesDir, "payload/$abi/libngzz.so")
        if (out.exists() && out.length() > 0) return out
        out.parentFile?.mkdirs()
        val candidate = java.io.File(context.applicationInfo.nativeLibraryDir, "libngzz.so")
        if (candidate.exists()) {
            candidate.copyTo(out, overwrite = true)
            return out
        }
        java.util.zip.ZipFile(context.applicationInfo.sourceDir).use { zip ->
            val entry = zip.getEntry("lib/$abi/libngzz.so")
                ?: error("未找到 $abi 版本的 libngzz.so")
            zip.getInputStream(entry).use { input ->
                out.outputStream().use { input.copyTo(it) }
            }
        }
        return out
    }
}
