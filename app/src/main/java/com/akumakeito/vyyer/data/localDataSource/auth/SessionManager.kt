package com.akumakeito.vyyer.data.localDataSource.auth

import android.content.SharedPreferences
import com.akumakeito.vyyer.domain.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

const val TOKEN = "accessToken"

@Singleton
class SessionManager @Inject constructor(
    private val prefs: SharedPreferences
) {

    private val _authStateFlow = MutableStateFlow<AuthState>(AuthState.Authenticated())

    init {
        val token = prefs.getString(TOKEN, null)
        if (token.isNullOrBlank()) {
            _authStateFlow.value = AuthState.Authenticated()
        } else {
            _authStateFlow.value = AuthState.Authenticated(true)
        }
    }

    val authStateFlow = _authStateFlow

    @Synchronized
    fun saveToken(token: String) {
        prefs.edit().putString(TOKEN, token).apply()
        _authStateFlow.value = AuthState.Authenticated(true)
    }

    @Synchronized
    fun getToken() =prefs.getString(TOKEN, null)



    @Synchronized
    fun deleteToken() {
        prefs.edit().remove(TOKEN).apply()
        _authStateFlow.value = AuthState.Authenticated()
    }


}