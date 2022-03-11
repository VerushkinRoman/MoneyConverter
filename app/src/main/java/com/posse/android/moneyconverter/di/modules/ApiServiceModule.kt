package com.posse.android.moneyconverter.di.modules

import android.content.SharedPreferences
import com.posse.android.moneyconverter.model.dataSource.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiServiceModule {

    @Provides
    @Singleton
    fun getHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun getRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    @Provides
    @Singleton
    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}