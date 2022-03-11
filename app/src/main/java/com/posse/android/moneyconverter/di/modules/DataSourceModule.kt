package com.posse.android.moneyconverter.di.modules

import com.posse.android.moneyconverter.di.annotations.Interactor
import com.posse.android.moneyconverter.di.annotations.Local
import com.posse.android.moneyconverter.model.DataSource
import com.posse.android.moneyconverter.model.InteractorImpl
import com.posse.android.moneyconverter.model.data.Response
import com.posse.android.moneyconverter.model.dataSource.RetrofitImpl
import com.posse.android.moneyconverter.model.dataSource.room.RoomImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("FunctionName")
@Module(
    includes = [
        ApiServiceModule::class,
        CacheModule::class
    ]
)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bind_RetrofitImpl_to_DataSource(retrofitImpl: RetrofitImpl): DataSource<Response>

    @Binds
    @Local
    @Singleton
    fun bind_RoomImpl_to_DataSource(roomImpl: RoomImpl): DataSource<Response>

    @Binds
    @Interactor
    fun bind_InteractorImpl_to_DataSource(interactorImpl: InteractorImpl): DataSource<Response>
}