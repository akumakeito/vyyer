package com.akumakeito.vyyer.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.GsonBuilder
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
    fun providesGson() = GsonBuilder()
        .setLenient()
        .create()


    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

//    @Provides
//    @Singleton
//    fun provideScanRemoteMediator(
//        @Named("ApiClient") apiService: ApiService,
//        identityScanInfoDao: IdentityScanInfoDao,
//        scanPageRemoteKeyDao: ScanPageRemoteKeyDao,
//        appDb: AppDb
//    ) = ScanRemoteMediator(apiService, identityScanInfoDao, scanPageRemoteKeyDao, appDb)
}