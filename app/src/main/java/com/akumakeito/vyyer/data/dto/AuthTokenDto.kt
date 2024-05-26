package com.akumakeito.vyyer.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("id_token")
    val idToken: String,
    val scope : String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("token_type")
    val tokenType: String,
)
