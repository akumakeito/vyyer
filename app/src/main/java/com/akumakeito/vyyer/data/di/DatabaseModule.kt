package com.akumakeito.vyyer.data.di

import android.content.Context
import androidx.room.Room
import com.akumakeito.vyyer.data.database.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDb = Room.databaseBuilder(
        context,
        AppDb::class.java,
        "vyyer.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideScanInfoDao(appDb : AppDb) = appDb.scanInfoDao()

    @Provides
    @Singleton
    fun provideIdentityInfoDao(appDb : AppDb) = appDb.identityInfoDao()

    @Provides
    @Singleton
    fun provideScanPageRemoteKeyDao(appDb : AppDb) = appDb.scanPageRemoteKeyDao()

}