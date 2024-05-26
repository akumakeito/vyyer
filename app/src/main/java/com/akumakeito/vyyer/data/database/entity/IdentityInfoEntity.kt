package com.akumakeito.vyyer.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akumakeito.vyyer.data.dto.IdentityInfoResponse
import com.akumakeito.vyyer.domain.model.Gender

@Entity(
    tableName = "identity_info"
)
data class IdentityInfoEntity(
    @PrimaryKey val identityId: Int,
    val fullName: String? = null,
    val gender : Gender
) {

    companion object {
        fun fromIdentityResponseToEntity( identityInfoResponse: IdentityInfoResponse) = IdentityInfoEntity (
            fullName = identityInfoResponse.fullName,
            identityId = identityInfoResponse.id,
            gender = Gender.entries.find {
                it.name.lowercase() == identityInfoResponse.gender.lowercase()} ?: Gender.OTHER
        )
    }

}
//
//fun List<IdentityScanInfoEntity>.toModel() : List<IdentityScanInfoModel> = map(IdentityScanInfoEntity::fromEntityToModel)
fun List<IdentityInfoResponse>.fromResponseToEntity() : List<IdentityInfoEntity> = map(IdentityInfoEntity::fromIdentityResponseToEntity)


