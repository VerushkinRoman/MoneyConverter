package com.posse.android.moneyconverter.di.modules

import com.posse.android.moneyconverter.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentModule::class])
interface ActivityModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}