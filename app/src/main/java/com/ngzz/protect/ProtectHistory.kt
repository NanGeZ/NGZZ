package com.ngzz.protect

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class ProtectHistory(private val context: Context) {

    private val historyFile = File(context.filesDir, "protect_history.json")

    fun load(): List<ProtectRecord> {
        if (!historyFile.exists()) return emptyList()
        return try {
            val json = historyFile.readText()
            val array = JSONArray(json)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                ProtectRecord(
                    apkName = obj.getString("apkName"),
                    outputPath = obj.getString("outputPath"),
                    success = obj.getBoolean("success"),
                    errorMessage = obj.optString("errorMessage").takeIf { it.isNotEmpty() },
                    timestamp = obj.getLong("timestamp")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun save(records: List<ProtectRecord>) {
        try {
            val array = JSONArray()
            records.forEach { record ->
                array.put(JSONObject().apply {
                    put("apkName", record.apkName)
                    put("outputPath", record.outputPath)
                    put("success", record.success)
                    put("errorMessage", record.errorMessage ?: "")
                    put("timestamp", record.timestamp)
                })
            }
            historyFile.writeText(array.toString())
        } catch (e: Exception) {
            // 静默失败，历史记录非关键
        }
    }

    fun add(record: ProtectRecord): List<ProtectRecord> {
        val current = load().toMutableList()
        current.add(0, record)
        val trimmed = if (current.size > MAX_RECORDS) current.take(MAX_RECORDS) else current
        save(trimmed)
        return trimmed
    }

    fun clear() {
        historyFile.delete()
    }

    companion object {
        const val MAX_RECORDS = 50
    }
}
