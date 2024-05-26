package com.akumakeito.vyyer.domain.state

sealed class AuthState {
    data class Authenticated(
        val isAuthenticated: Boolean = false,
        val audience: String? = null) : AuthState()

    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}