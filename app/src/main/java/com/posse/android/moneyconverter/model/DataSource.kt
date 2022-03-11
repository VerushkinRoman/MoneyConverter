package com.posse.android.moneyconverter.model

interface DataSource<T> {
    suspend fun getData(isOnline: Boolean): T
    fun saveData(data:T)
}