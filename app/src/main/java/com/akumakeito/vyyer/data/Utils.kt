package com.akumakeito.vyyer.data

import android.content.Context
import com.akumakeito.vyyer.R
import com.akumakeito.vyyer.data.dto.ErrorAuthDto
import com.akumakeito.vyyer.domain.state.AuthState

fun parseError(errorBody : String?, context : Context) : AuthState.Error {
    val error = errorBody?.let { ErrorAuthDto.fromJson(it) }
    return AuthState.Error(error?.errorDescription ?: context.getString(R.string.unknown_error))
}