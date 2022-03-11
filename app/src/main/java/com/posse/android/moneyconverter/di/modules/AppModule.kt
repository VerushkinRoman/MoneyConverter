package com.posse.android.moneyconverter.di.modules

import android.content.Context
import com.posse.android.moneyconverter.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun app(): Context = app
}