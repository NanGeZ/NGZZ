package com.tianmen.ngz.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

object FileImporter {

    fun copyToDir(context: Context, uri: Uri, dir: File, fallbackName: String): File {
        if (!dir.exists() && !dir.mkdirs()) {
            throw IllegalStateException("无法创建目录: ${dir.absolutePath}")
        }
        val name = displayName(context, uri) ?: fallbackName
        val target = uniqueFile(dir, sanitize(name))
        context.contentResolver.openInputStream(uri)?.use { input ->
            target.outputStream().use { output -> input.copyTo(output) }
        } ?: throw IllegalStateException("无法读取所选文件")
        return target
    }

    fun displayName(context: Context, uri: Uri): String? {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index >= 0 && cursor.moveToFirst()) {
                return cursor.getString(index)
            }
        }
        return uri.lastPathSegment
    }

    private fun uniqueFile(dir: File, name: String): File {
        val dot = name.lastIndexOf('.')
        val base = if (dot > 0) name.substring(0, dot) else name
        val ext = if (dot > 0) name.substring(dot) else ""
        var candidate = File(dir, name)
        var counter = 1
        while (candidate.exists()) {
            candidate = File(dir, "${base}_$counter$ext")
            counter++
        }
        return candidate
    }

    private fun sanitize(name: String): String =
        name.replace(Regex("[/\\\\:\u0000]"), "_").ifBlank { "imported" }
}
