package com.akumakeito.vyyer.data.network

import com.akumakeito.vyyer.data.dto.IdentityInfoResponse
import com.akumakeito.vyyer.data.dto.IdentityRequestBody
import com.akumakeito.vyyer.data.dto.ScanInfoResponse
import com.akumakeito.vyyer.data.dto.ScanRequestPaging
import com.akumakeito.vyyer.data.dto.ServerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ApiService {


    @POST("/api/v2/scans/get")
    suspend fun getScanInfoList(@Body request: ScanRequestPaging): Response<ServerResponse<ScanInfoResponse>>


    @POST("/api/v2/identities/get")
    suspend fun getIdentityInfoList(@Body request: IdentityRequestBody): Response<ServerResponse<IdentityInfoResponse>>

    @Streaming
    @GET("/api/v2/identities/avatar")
    @Headers("Content-Type: image/jpg")
    suspend fun getIdentityAvatar(@Query("id") identityId : Int) : ResponseBody



}