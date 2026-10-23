package com.tianmen.ngz.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProtectTask::class], version = 1)
abstract class NgzDatabase : RoomDatabase() {
    abstract fun protectTaskDao(): ProtectTaskDao
}
