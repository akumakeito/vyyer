package com.akumakeito.vyyer.domain.model

data class IdentityScanInfoModel(
    val id : Int,
    val scanId : Int,
    val identityId : Int,
    val fullName : String?,
    val createdAt : String,
    val verdictName : String,
    val gender: Gender
)

enum class Gender {
    MALE,
    FEMALE,
    OTHER
}