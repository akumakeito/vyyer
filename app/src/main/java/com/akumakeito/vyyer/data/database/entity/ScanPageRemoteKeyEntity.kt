package com.akumakeito.vyyer.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class ScanPageRemoteKeyEntity(
    @PrimaryKey
    val id : Int = 1,
    val prevPage : Int?,
    val nextPage : Int?
)
