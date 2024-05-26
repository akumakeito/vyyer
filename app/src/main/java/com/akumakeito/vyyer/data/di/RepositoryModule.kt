package com.akumakeito.vyyer.data.di

import com.akumakeito.vyyer.data.remoteDataSource.auth.AuthRepositoryImpl
import com.akumakeito.vyyer.data.remoteDataSource.scan.ScanRepositoryImpl
import com.akumakeito.vyyer.domain.repository.AuthRepository
import com.akumakeito.vyyer.domain.repository.ScanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindScanRepository(scanRepositoryImpl: ScanRepositoryImpl): ScanRepository
}