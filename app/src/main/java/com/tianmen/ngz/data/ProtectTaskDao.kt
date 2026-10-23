package com.tianmen.ngz.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProtectTaskDao {
    @Insert
    suspend fun insert(task: ProtectTask): Long

    @Query("SELECT * FROM ProtectTask ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<ProtectTask>>
}
