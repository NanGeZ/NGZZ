package com.ngzz.protect

import java.io.File
import java.util.zip.ZipFile

object ApkIo {

    fun entryNames(apk: String): List<String> {
        val src = File(apk)
        check(src.exists()) { "输入 APK 不存在" }
        ZipFile(src).use { zip ->
            return zip.entries().toList().map { it.name }
        }
    }
}
