package com.akumakeito.vyyer.data.dto

data class ErrorApiDto(
    val context : String,
    val message : String,
    val description : String,
    val code : Int
)
