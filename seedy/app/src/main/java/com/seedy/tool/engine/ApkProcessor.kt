package com.seedy.tool.engine

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipFile

/**
 * 过签处理引擎（免 root）：
 *   1. 解析输入 APK（v1/v2/v3 签名方案、原始证书）
 *   2. 重写 APK zip：剥离旧 v1 签名块 → 写入原签名记录 `_.s`
 *      （方案 A/B 另注入 assets/hook.dex 运行时载荷 + `_.m` 方案标记）
 *   3. 输出到所选 APK 同目录，文件名 <原名>.out.apk
 *
 * 输出【不签名】，由用户自行签名。
 */
class ApkProcessor(private val context: Context) {

    private val cacheDir: File get() = context.cacheDir

    /** 处理入口。onProgress 回传进度文案（工作线程）。 */
    suspend fun process(
        inputUri: Uri,
        plan: Plan,
        onProgress: (String) -> Unit = {},
    ): ProcessResult = withContext(Dispatchers.IO) {
        try {
            onProgress("读取 APK…")
            val input = copyToCache(inputUri, "seedy_input.apk", onProgress)

            onProgress("解析签名…")
            val schemes = SigningSigs.detectSchemes(input)
            val certs = SigningSigs.extract(input)
            if (certs.isEmpty()) {
                return@withContext ProcessResult.Error("未能提取到签名证书，无法生成 _.s")
            }
            val analysis = ApkAnalysis(
                schemes = schemes,
                certs = certs.map { CertInfo(it, SigningSigs.sha256Fingerprint(it), "APK 签名") },
                entryCount = runCatching { ZipFile(input).use { it.size() } }.getOrDefault(0),
                sizeBytes = input.length(),
            )

            onProgress("注入过签数据…")
            val output = File(cacheDir, "seedy_output.apk")
            val injected = rebuild(input, output, plan, certs, onProgress)

            onProgress("完成")
            val display = queryName(inputUri)
            val suggested = suggestedName(display)
            // 优先写到所选 APK 同目录；无权限时回退让用户选目录
            val savedTo = trySaveBesideSource(inputUri, output, suggested)
            ProcessResult.Success(
                plan = plan,
                analysis = analysis,
                injected = injected,
                outputName = suggested,
                savedTo = savedTo,
                pendingFile = if (savedTo == null) output else null,
            )
        } catch (t: Throwable) {
            ProcessResult.Error(t.message ?: t.javaClass.simpleName)
        }
    }

    /** 用户在 SAF 里选定输出位置后，把缓存结果落盘。 */
    suspend fun saveTo(target: Uri, pending: File): String? = withContext(Dispatchers.IO) {
        runCatching {
            context.contentResolver.openOutputStream(target, "wt")?.use { out ->
                pending.inputStream().use { it.copyTo(out) }
            } ?: error("无法打开输出流")
            pending.delete()
            target.toString()
        }.getOrNull()
    }

    // ---------------------------------------------------------------- steps

    private fun copyToCache(uri: Uri, name: String, onProgress: (String) -> Unit): File {
        val dst = File(cacheDir, name)
        context.contentResolver.openInputStream(uri)?.use { ins ->
            dst.outputStream().use { out ->
                val buf = ByteArray(256 * 1024)
                var total = 0L
                while (true) {
                    val n = ins.read(buf)
                    if (n < 0) break
                    out.write(buf, 0, n)
                    total += n
                    if (total % (4L * 1024 * 1024) < 256 * 1024) {
                        onProgress("读取 APK… ${total / 1024 / 1024} MB")
                    }
                }
            }
        } ?: error("无法读取所选文件")
        return dst
    }

    private fun rebuild(
        input: File,
        output: File,
        plan: Plan,
        certs: List<ByteArray>,
        onProgress: (String) -> Unit,
    ): List<String> {
        val injected = mutableListOf<String>()
        val sigEntry = SigningSigs.serializeSigEntry(certs)
        val hookDex: ByteArray? = if (plan == Plan.PMS_HOOK || plan == Plan.EARLY_INJECT) {
            runCatching { context.assets.open("hook.dex").readBytes() }.getOrNull()
        } else null

        ZipFile(input).use { z ->
            val writer = AlignedZipWriter(output.outputStream().buffered())
            val injectedNames = mutableSetOf("_.s", "_.m", "assets/hook.dex")
            for (e in z.entries()) {
                val name = e.name
                if (name in injectedNames) continue
                if (isV1SignatureEntry(name)) continue // 剥离旧 v1 签名块
                val data = z.getInputStream(e).use { it.readBytes() }
                writer.put(name, data, store = e.method == 0, timeMs = e.time)
            }
            writer.put("_.s", sigEntry, store = false)
            injected += "_.s"
            if (hookDex != null) {
                writer.put("assets/hook.dex", hookDex, store = false)
                injected += "assets/hook.dex"
                writer.put("_.m", plan.code.toByteArray(), store = false)
                injected += "_.m"
            } else if (plan == Plan.PMS_HOOK || plan == Plan.EARLY_INJECT) {
                error("内置载荷 hook.dex 缺失")
            }
            writer.finish()
        }
        onProgress("已生成处理后 APK")
        return injected
    }

    /** 输出到所选 APK 同目录；失败返回 null（由 UI 走 SAF 选目录流程）。 */
    private fun trySaveBesideSource(inputUri: Uri, output: File, name: String): String? {
        return runCatching {
            val docId = DocumentsContract.getDocumentId(inputUri) ?: return null
            val sep = docId.lastIndexOf('/')
            if (sep <= 0) return null
            val parentId = docId.substring(0, sep)
            val parentUri = DocumentsContract.buildDocumentUri(inputUri.authority, parentId)
            val created = DocumentsContract.createDocument(
                context.contentResolver, parentUri,
                "application/vnd.android.package-archive", name
            ) ?: return null
            context.contentResolver.openOutputStream(created, "wt")?.use { out ->
                output.inputStream().use { it.copyTo(out) }
            } ?: return null
            output.delete()
            created.toString()
        }.getOrNull()
    }

    private fun isV1SignatureEntry(name: String): Boolean {
        val n = name.uppercase()
        if (!n.startsWith("META-INF/")) return false
        return n.endsWith(".SF") || n.endsWith(".RSA") || n.endsWith(".DSA") ||
            n.endsWith(".EC") || n == "META-INF/MANIFEST.MF"
    }

    private fun queryName(uri: Uri): String {
        context.contentResolver.query(uri, null, null, null, null)?.use { c ->
            if (c.moveToFirst()) {
                val idx = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (idx >= 0) return c.getString(idx)
            }
        }
        return uri.lastPathSegment ?: "input.apk"
    }

    private fun suggestedName(display: String): String {
        val base = display.removeSuffix(".apk").removeSuffix(".APK")
        return "$base.out.apk"
    }
}
