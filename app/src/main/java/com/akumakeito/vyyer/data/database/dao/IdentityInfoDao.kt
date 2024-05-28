package com.akumakeito.vyyer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akumakeito.vyyer.data.database.entity.IdentityInfoEntity

@Dao
interface IdentityInfoDao {
    @Query("SELECT * FROM identity_info")
    fun getAllIdentityInfo(): List<IdentityInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(identityInfo: List<IdentityInfoEntity>)

    @Query("SELECT * FROM identity_info WHERE identityId = :id")
    suspend fun getIdentityById(id : Int) : IdentityInfoEntity

    @Query("SELECT identityId FROM identity_info")
    suspend fun getAllIdentityId() : List<Int>

    @Query("UPDATE identity_info SET fullName = :name WHERE identityId = :id")
    suspend fun updateName(id: Int, name: String)

    @Query("DELETE FROM identity_info")
    suspend fun clearAll()
}