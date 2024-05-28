package com.akumakeito.vyyer.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

const val DEFAULT_PER_PAGE = 10

@Serializable
data class ScanRequestPaging(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int = DEFAULT_PER_PAGE,
)

