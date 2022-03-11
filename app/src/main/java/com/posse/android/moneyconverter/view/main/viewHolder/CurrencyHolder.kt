package com.posse.android.moneyconverter.view.main.viewHolder

import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.posse.android.moneyconverter.databinding.CurrencyCardBinding
import com.posse.android.moneyconverter.model.data.Currency
import com.posse.android.moneyconverter.view.main.adapter.Adapter

class CurrencyHolder(
    private val binding: CurrencyCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Currency, listener: Adapter.OnListItemClickListener) = with(binding) {
        setCardData(data)
        setListener(listener)
        setMoneyConverter(data)
    }

    private fun setCardData(data: Currency) = with(binding) {
        name.text = data.Name
        "(${data.CharCode})".also { charCode.text = it }
        price.text = data.Value.toString()
        quantity.text = data.Nominal.toString()
    }

    private fun setListener(listener: Adapter.OnListItemClickListener) = with(binding) {
        root.setOnClickListener {
            currencyConverter.visibility = if (currencyConverter.isVisible) View.GONE
            else View.VISIBLE
            listener.onItemClick()
        }
    }

    private fun setMoneyConverter(data: Currency) = with(binding) {
        currencyConverterLayout.setEndIconOnClickListener {
            currencyEditText.setText("")
            result.text = "0"
            currencyConverterLayout.isEndIconVisible = false
        }

        currencyConverterLayout.editText?.doOnTextChanged { _, _, _, count ->
            currencyConverterLayout.isEndIconVisible = count <= 0
            val inputNumber = currencyEditText.text.toString().toDoubleOrNull() ?: 0.0
            val price = convertMoney(data, inputNumber)
            result.text = price.toString()
        }

    }

    private fun convertMoney(data: Currency, rub: Double): Double = data.Value * data.Nominal * rub
}