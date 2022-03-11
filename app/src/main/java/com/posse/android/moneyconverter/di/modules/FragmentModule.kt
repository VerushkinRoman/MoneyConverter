package com.posse.android.moneyconverter.di.modules

import com.posse.android.moneyconverter.view.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
interface FragmentModule {

    @ContributesAndroidInjector
    fun contributeMainFragment(): MainFragment
}