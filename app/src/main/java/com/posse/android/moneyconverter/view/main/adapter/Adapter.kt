package com.posse.android.moneyconverter.view.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.posse.android.moneyconverter.databinding.CurrencyCardBinding
import com.posse.android.moneyconverter.model.data.Currency
import com.posse.android.moneyconverter.view.main.viewHolder.CurrencyHolder

class Adapter : RecyclerView.Adapter<CurrencyHolder>() {

    private val displayData: MutableList<Currency> = mutableListOf()

    fun setSingleData(data: Currency) {
        displayData.add(data)
        notifyItemInserted(displayData.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        displayData.clear()
        notifyDataSetChanged()
    }

    fun getData(): List<Currency> = displayData

    override fun getItemCount(): Int = displayData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val binding = CurrencyCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CurrencyHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(displayData[position]){
            notifyItemChanged(position)
        }
    }

    fun interface OnListItemClickListener {
        fun onItemClick()
    }
}
