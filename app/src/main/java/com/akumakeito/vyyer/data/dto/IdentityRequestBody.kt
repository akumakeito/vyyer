package com.akumakeito.vyyer.data.dto

import com.google.gson.annotations.SerializedName

data class IdentityRequestBody(
    @SerializedName("IDs")
    val ids : List<Int>
)
