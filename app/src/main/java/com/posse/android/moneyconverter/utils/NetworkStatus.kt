package com.posse.android.moneyconverter.utils

import kotlinx.coroutines.flow.StateFlow

interface NetworkStatus {
    fun isOnline(): StateFlow<Boolean?>
}