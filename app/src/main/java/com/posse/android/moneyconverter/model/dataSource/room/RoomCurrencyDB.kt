package com.posse.android.moneyconverter.model.dataSource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.posse.android.moneyconverter.model.data.Currency

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class RoomCurrencyDB : RoomDatabase() {

    abstract val currencyDao: CurrencyDao
}