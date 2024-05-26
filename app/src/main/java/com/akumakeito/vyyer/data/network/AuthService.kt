package com.akumakeito.vyyer.data.network

import com.akumakeito.vyyer.data.dto.AccessTokenDto
import com.akumakeito.vyyer.data.dto.AuthTokenDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun getAudience(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<AuthTokenDto>


    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("audience") audience: String
    ): Response<AccessTokenDto>
}