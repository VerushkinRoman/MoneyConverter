package com.posse.android.moneyconverter.model.dataSource

import com.posse.android.moneyconverter.model.data.Response
import retrofit2.http.GET

interface ApiService {

    @GET("daily_json.js")
    suspend fun getDaily(): Response
}