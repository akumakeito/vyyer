package com.akumakeito.vyyer.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akumakeito.vyyer.data.dto.ScanInfoResponse
import com.akumakeito.vyyer.domain.model.Gender
import com.akumakeito.vyyer.domain.model.IdentityScanInfoModel

@Entity(tableName = "scans_info",
//    foreignKeys = [ForeignKey(
//        entity = IdentityInfoEntity::class,
//        parentColumns = ["identityId"],
//        childColumns = ["identityId"],
//        onDelete = ForeignKey.CASCADE
//    )])
)
data class ScanInfoEntity(
    val scanId : Int,
    val identityId : Int,
    val createdAt : String,
    val verdictName : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
) {
    companion object {
        fun fromScanResponseToEntity(scanInfoResponse: ScanInfoResponse) = ScanInfoEntity(
            scanId = scanInfoResponse.id,
            identityId = scanInfoResponse.identityID,
            createdAt = scanInfoResponse.createdAt,
            verdictName = scanInfoResponse.verdictName
        )
    }


    fun fromEntityToModel() = IdentityScanInfoModel(
        id = id,
        identityId = identityId,
        createdAt = createdAt,
        verdictName = verdictName,
        fullName = "unknown",
        gender = Gender.OTHER
    )
}


fun List<ScanInfoEntity>.toModel() : List<IdentityScanInfoModel> = map(ScanInfoEntity::fromEntityToModel)
fun List<ScanInfoResponse>.fromResponseToEntity() : List<ScanInfoEntity> = map(ScanInfoEntity::fromScanResponseToEntity)
