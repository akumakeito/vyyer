package com.akumakeito.vyyer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akumakeito.vyyer.data.database.dao.IdentityInfoDao
import com.akumakeito.vyyer.data.database.dao.ScanInfoDao
import com.akumakeito.vyyer.data.database.dao.ScanPageRemoteKeyDao
import com.akumakeito.vyyer.data.database.entity.IdentityInfoEntity
import com.akumakeito.vyyer.data.database.entity.ScanInfoEntity
import com.akumakeito.vyyer.data.database.entity.ScanPageRemoteKeyEntity

@Database(entities = [ScanInfoEntity::class, ScanPageRemoteKeyEntity::class, IdentityInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase(){

    abstract fun scanInfoDao(): ScanInfoDao

    abstract fun scanPageRemoteKeyDao(): ScanPageRemoteKeyDao

    abstract fun identityInfoDao(): IdentityInfoDao


}