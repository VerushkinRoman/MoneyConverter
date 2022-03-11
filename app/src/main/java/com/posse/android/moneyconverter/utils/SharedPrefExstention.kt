package com.posse.android.moneyconverter.utils

import android.content.SharedPreferences

var SharedPreferences.syncTime: Long
    get() = this.getLong("time", -1)
    set(value) {
        this.edit()
            .putLong("time", value)
            .apply()
    }

