package com.akumakeito.vyyer.data.remoteDataSource.auth

import android.content.Context
import android.util.Log
import com.akumakeito.vyyer.BuildConfig
import com.akumakeito.vyyer.data.localDataSource.auth.SessionManager
import com.akumakeito.vyyer.data.localDataSource.auth.TOKEN
import com.akumakeito.vyyer.data.network.AuthService
import com.akumakeito.vyyer.data.parseError
import com.akumakeito.vyyer.domain.repository.AuthRepository
import com.akumakeito.vyyer.domain.state.AuthState
import com.auth0.android.jwt.JWT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager,
    @ApplicationContext private val context: Context
) : AuthRepository {

    override val authStateFlow = sessionManager.authStateFlow

    override suspend fun login(username: String, password: String): AuthState {
        return try {
            val result = getAudience(username, password)

            when (result) {
                is AuthState.Authenticated -> {
                    if (result.audience != null) {
                        getAccessTokenForClient(BuildConfig.BASE_URL_API, username, password)
                    } else {
                        AuthState.Error("no audience")
                    }
                }

                is AuthState.Error -> return AuthState.Error(result.message)
                AuthState.Loading -> {
                    AuthState.Authenticated()
                }
            }
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "")
        }
    }


    private suspend fun getAudience(username: String, password: String): AuthState {

        return try {
            val response = authService.getAudience(
                "password",
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                username,
                password
            )

            if (response.isSuccessful) {
                val token = response.body()?.idToken
                val jwt = JWT(token ?: "")
                val audience = jwt.claims["aud"]?.asString() ?: ""
                Log.d(TOKEN, "id_token : $token}")

                AuthState.Authenticated(audience = audience)
            } else {

                parseError(response.errorBody()?.string(), context)
            }
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Something went wrong")
        }

    }


    private suspend fun getAccessTokenForClient(
        audience: String,
        username: String,
        password: String
    ): AuthState {
        return try {
            val response = authService.getAccessToken(
                "password",
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                username,
                password,
                audience
            )

            if (response.isSuccessful) {
                val token = response.body()?.accessToken
                Log.d(TOKEN, "access token : $token")
                sessionManager.saveToken(token ?: "")
                Log.d(TOKEN, "access token from session manager: ${sessionManager.getToken()}")
                AuthState.Authenticated(true)
            } else {
                parseError(response.errorBody()?.string(), context)
            }
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "")
        }

    }
}
