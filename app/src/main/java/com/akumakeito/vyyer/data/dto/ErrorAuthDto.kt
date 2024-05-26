package com.akumakeito.vyyer.data.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorAuthDto(
    val error: String,
    @SerializedName("error_description")
    val errorDescription: String
) {



    companion object {
        private val gson = Gson()

        fun fromJson(json: String): ErrorAuthDto {
            return gson.fromJson(json, ErrorAuthDto::class.java)
        }
    }
}