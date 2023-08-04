package com.example.testindocyber.screen.qr.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testindocyber.databinding.ItemHistoryBinding
import com.example.testindocyber.model.network.room.Payment

class AdapterHistory(private val context: Context ): RecyclerView.Adapter<AdapterHistory.ViewHolder>() {

    private val listPayment = mutableListOf<Payment>()
    inner class ViewHolder(val view: ItemHistoryBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(data: Payment){
            view.tvNameTrans.text = data.nameTransaction
            view.tvAmount.text = data.amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHistory.ViewHolder = ViewHolder(
        ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
    )

    override fun onBindViewHolder(holder: AdapterHistory.ViewHolder, position: Int) {
        holder.bind(listPayment[position])
    }

    override fun getItemCount(): Int {
        return listPayment.size
    }

    fun setDataHistory(data: ArrayList<Payment>){
        this.listPayment.clear()
        this.listPayment.addAll(data)
        notifyDataSetChanged()
    }
}