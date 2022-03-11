package com.posse.android.moneyconverter.di.component

import android.app.Application
import com.posse.android.moneyconverter.app.App
import com.posse.android.moneyconverter.di.modules.ActivityModule
import com.posse.android.moneyconverter.di.modules.AppModule
import com.posse.android.moneyconverter.di.modules.NetworkModule
import com.posse.android.moneyconverter.di.modules.SharedModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ActivityModule::class,
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        SharedModule::class,
        NetworkModule::class
    ]
)

@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}