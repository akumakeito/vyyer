package com.akumakeito.vyyer.data.network

import com.akumakeito.vyyer.data.localDataSource.auth.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiAuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    val token = sessionManager.getToken()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("X-User-Id", "Auth0User")
            .addHeader("X-Org-Id", "Auth0Org")
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}