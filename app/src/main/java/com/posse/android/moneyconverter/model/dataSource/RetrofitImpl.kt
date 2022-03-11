package com.posse.android.moneyconverter.model.dataSource

import com.posse.android.moneyconverter.model.DataSource
import com.posse.android.moneyconverter.model.data.Response
import javax.inject.Inject

class RetrofitImpl @Inject constructor(
    private val apiService: ApiService
) : DataSource<Response> {

    override suspend fun getData(isOnline: Boolean): Response = apiService.getDaily()

    override fun saveData(data: Response) = Unit
}