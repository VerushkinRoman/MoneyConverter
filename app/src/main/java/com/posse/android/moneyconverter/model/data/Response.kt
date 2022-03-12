package com.posse.android.moneyconverter.model.data

data class Response(
    val Timestamp: String?,
    val Valute: Map<String, Currency>
)