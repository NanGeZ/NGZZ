package com.tianmen.ngz.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProtectTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val inputPath: String,
    val outputPath: String,
    val outputUri: String,
    val packageName: String,
    val status: String,
    val signed: Boolean,
    val createdAt: Long,
)
