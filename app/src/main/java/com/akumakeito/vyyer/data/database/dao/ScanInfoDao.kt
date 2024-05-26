package com.akumakeito.vyyer.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.akumakeito.vyyer.data.database.entity.ScanInfoEntity
import com.akumakeito.vyyer.data.database.entity.ScanWithIdentity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanInfoDao {

    @Query("SELECT COUNT(*)==0 FROM scans_info")
    suspend fun isEmpty(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(scanInfo: List<ScanInfoEntity>)

    @Transaction
    @Query("SELECT * FROM scans_info")
    fun getScansWithIdentity(): List<ScanWithIdentity>

    @Query("SELECT * FROM scans_info")
    fun getScans(): List<ScanInfoEntity>

    @Query("SELECT identityId FROM scans_info")
    fun getNewScansIdentityID(): Flow<List<Int>>

    @Query("SELECT * FROM scans_info ORDER BY createdAt DESC")
    fun pagingSource(): PagingSource<Int, ScanInfoEntity>

    @Query("DELETE FROM scans_info")
    suspend fun clearAll()
}