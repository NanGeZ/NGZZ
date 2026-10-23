package com.tianmen.ngz.di

import android.content.Context
import androidx.room.Room
import com.tianmen.ngz.data.NgzDatabase
import com.tianmen.ngz.settings.DataStoreSettingsStore
import com.tianmen.ngz.settings.SettingsRepository
import com.tianmen.ngz.settings.SettingsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsStore(@ApplicationContext context: Context): SettingsStore =
        DataStoreSettingsStore(context)

    @Provides
    @Singleton
    fun provideSettingsRepository(store: SettingsStore): SettingsRepository =
        SettingsRepository(store)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NgzDatabase =
        Room.databaseBuilder(context, NgzDatabase::class.java, "ngzz.db").build()
}
