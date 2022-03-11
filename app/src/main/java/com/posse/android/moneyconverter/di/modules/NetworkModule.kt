package com.posse.android.moneyconverter.di.modules

import com.posse.android.moneyconverter.utils.AndroidNetworkStatus
import com.posse.android.moneyconverter.utils.NetworkStatus
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @Suppress("FunctionName")
    @Binds
    fun bindAndroidNetworkStatus_to_NetworkStatus(androidNetworkStatus: AndroidNetworkStatus): NetworkStatus
}