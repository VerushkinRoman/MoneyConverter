package com.posse.android.moneyconverter.model.dataSource.room

import com.posse.android.moneyconverter.model.DataSource
import com.posse.android.moneyconverter.model.data.Currency
import com.posse.android.moneyconverter.model.data.Response
import com.posse.android.moneyconverter.model.data.Valute
import javax.inject.Inject

class RoomImpl @Inject constructor(
    private val dataBase: RoomCurrencyDB
) : DataSource<Response> {

    override suspend fun getData(isOnline: Boolean): Response {
        val valutes: MutableMap<String, Currency> = mutableMapOf()
        val currencies = dataBase.currencyDao.getAll()
        currencies.forEach {
            valutes[it.CharCode] = it
        }
        return Response(null, Valute(valutes))
    }

    override fun saveData(data: Response) {
        dataBase.currencyDao.insert(data.Valute.valutes.values.toList())
    }
}