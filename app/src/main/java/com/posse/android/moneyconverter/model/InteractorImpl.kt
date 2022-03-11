package com.posse.android.moneyconverter.model

import com.posse.android.moneyconverter.di.annotations.Local
import com.posse.android.moneyconverter.model.data.Response
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val remoteDataSource: DataSource<Response>,
    @Local private val localDataSource: DataSource<Response>
) : DataSource<Response> {

    override suspend fun getData(isOnline: Boolean): Response {
        return if (isOnline) {
            val data = remoteDataSource.getData(isOnline)
            saveData(data)
            data
        } else localDataSource.getData(isOnline)
    }

    override fun saveData(data: Response) = localDataSource.saveData(data)
}