package com.akumakeito.vyyer.data.network

import com.akumakeito.vyyer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CredentialsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .addQueryParameter("client_secret", BuildConfig.CLIENT_SECRET)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }


}