package com.posse.android.moneyconverter.model.dataSource.room

import androidx.room.*
import com.posse.android.moneyconverter.model.data.Currency

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg currencies: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencies: List<Currency>)

    @Query("DELETE FROM ${Currency.TABLE_NAME} WHERE id = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM ${Currency.TABLE_NAME}")
    fun deleteAll()

    @Delete
    fun delete(currency: Currency)

    @Delete
    fun delete(vararg currencies: Currency)

    @Delete
    fun delete(currencies: List<Currency>)

    @Query("SELECT * FROM ${Currency.TABLE_NAME}")
    fun getAll(): List<Currency>

    @Query("SELECT * FROM ${Currency.TABLE_NAME} WHERE id = :id")
    fun getById(id: Int): List<Currency>?

    @Query("SELECT COUNT(id) FROM ${Currency.TABLE_NAME}")
    fun getCount(): Int
}