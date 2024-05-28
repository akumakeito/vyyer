package com.akumakeito.vyyer.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.akumakeito.vyyer.domain.model.IdentityScanInfoModel

data class ScanWithIdentity(
    @Embedded val scanInfo : ScanInfoEntity,
    @Relation(
        parentColumn = "identityId",
        entityColumn = "identityId",
    )
    val identityInfo : IdentityInfoEntity
) {
    fun fromEntityToModel() = IdentityScanInfoModel(
        id = scanInfo.id,
        identityId = scanInfo.identityId,
        fullName = identityInfo.fullName,
        createdAt = scanInfo.createdAt,
        verdictName = scanInfo.verdictName,
        gender = identityInfo.gender
    )
}
