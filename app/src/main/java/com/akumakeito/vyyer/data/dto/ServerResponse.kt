package com.akumakeito.vyyer.data.dto

import com.google.gson.annotations.SerializedName

data class ServerResponse<T>(
    @SerializedName("Data") val data: List<T>,
    @SerializedName("Errors") val errors: List<String>?,
    @SerializedName("Pagination") val pagination: Pagination
)
