package com.akumakeito.vyyer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akumakeito.vyyer.data.database.entity.ScanPageRemoteKeyEntity

@Dao
interface ScanPageRemoteKeyDao {
    @Query("SELECT COUNT(*)==0 FROM remote_keys")
    suspend fun isEmpty() : Boolean

    @Query("SELECT nextPage FROM remote_keys")
    suspend fun getNextPageNumber() : Int?

    @Query("SELECT prevPage FROM remote_keys")
    suspend fun getPrevPageNumber() : Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: ScanPageRemoteKeyEntity)

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}
