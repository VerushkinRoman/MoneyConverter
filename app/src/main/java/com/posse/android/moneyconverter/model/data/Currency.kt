package com.posse.android.moneyconverter.model.data

import android.os.Parcelable
import androidx.room.Entity
import com.posse.android.moneyconverter.model.data.Currency.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class Currency(
    val CharCode: String,
    @PrimaryKey val ID: String,
    val Name: String,
    val Nominal: Int,
    val NumCode: String,
    val Previous: Double,
    val Value: Double
) : Parcelable {
    companion object {
        const val TABLE_NAME = "Currency"
    }
}