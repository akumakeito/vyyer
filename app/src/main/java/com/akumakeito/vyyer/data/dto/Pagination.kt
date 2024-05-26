package com.akumakeito.vyyer.data.dto

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("CurrentPage") val currentPage: Int,
    @SerializedName("PerPage") val perPage: Int,
    @SerializedName("Count") val count: Int,
    @SerializedName("Pages") val pages: Int
)
