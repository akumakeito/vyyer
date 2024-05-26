package com.akumakeito.vyyer.domain.repository

import com.akumakeito.vyyer.domain.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow

interface AuthRepository {

    val authStateFlow : MutableStateFlow<AuthState>

    suspend fun login(username : String, password:String) : AuthState
}