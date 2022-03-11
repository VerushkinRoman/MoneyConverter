package com.posse.android.moneyconverter.di.modules

import android.content.Context
import androidx.room.Room
import com.posse.android.moneyconverter.model.dataSource.room.RoomCurrencyDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun db(context: Context): RoomCurrencyDB {
        return Room.databaseBuilder(context, RoomCurrencyDB::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}