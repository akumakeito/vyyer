package com.akumakeito.vyyer.data.dto

import com.google.gson.annotations.SerializedName

data class ScanInfoResponse(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("UserID")
    val userID: String,
    @SerializedName("IdentityID")
    val identityID: Int,
    @SerializedName("CreatedAt")
    val createdAt: String,
    @SerializedName("Flags")
    val flags: Int,
    @SerializedName("VerdictType")
    val verdictType: Int,
    @SerializedName("VerdictResult")
    val verdictResult: Int,
    @SerializedName("VerdictName")
    val verdictName: String,
    @SerializedName("VerdictValue")
    val verdictValue: String,
    @SerializedName("VIP")
    val vip: Int,
    @SerializedName("Ban")
    val ban: Int
)