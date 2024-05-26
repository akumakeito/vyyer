package com.akumakeito.vyyer.data.di;


import com.akumakeito.vyyer.BuildConfig
import com.akumakeito.vyyer.data.network.ApiAuthInterceptor
import com.akumakeito.vyyer.data.network.ApiService
import com.akumakeito.vyyer.data.network.AuthService
import com.akumakeito.vyyer.data.network.CredentialsInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val BASE_URL_AUTH = BuildConfig.BASE_URL_AUTH
    private val BASE_URL_API = BuildConfig.BASE_URL_API

    @Provides
    @Singleton
    fun providesLogging() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideCredentialsInterceptor() = CredentialsInterceptor()

    @Provides
    @Singleton
    @Named("AuthClient")
    fun providesAuthOkhttpClient(
        logging: HttpLoggingInterceptor,
        credentialsInterceptor: CredentialsInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(logging)
//        .addInterceptor(credentialsInterceptor)
        .build()

    @Provides
    @Singleton
    @Named("ApiClient")
    fun providesApiOkhttpClient(
        logging: HttpLoggingInterceptor,
        authInterceptor: ApiAuthInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        .build()


    @Provides
    @Singleton
    @Named("AuthClient")
    fun provideAuthRetrofit(@Named("AuthClient") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_AUTH)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthService(@Named("AuthClient") retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    @Named("ApiClient")
    fun provideApiRetrofit(@Named("ApiClient") okHttpClient: OkHttpClient, gson : Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideApiService(@Named("ApiClient") retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
