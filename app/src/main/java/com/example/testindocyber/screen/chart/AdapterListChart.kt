package com.example.testindocyber.screen.chart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testindocyber.databinding.ItemHistoryBinding
import com.example.testindocyber.model.network.DetailDataDonut

class AdapterListChart(private val context: Context): RecyclerView.Adapter<AdapterListChart.ViewHolder>() {

    private val listTransaction = mutableListOf<DetailDataDonut>()
    inner class ViewHolder(val view: ItemHistoryBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(data: DetailDataDonut){
            view.tvNameTrans.text = data.trxDate
            view.tvAmount.text = data.nominal.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListChart.ViewHolder = ViewHolder(
        ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
    )

    override fun onBindViewHolder(holder: AdapterListChart.ViewHolder, position: Int) {
        holder.bind(listTransaction[position])
    }

    override fun getItemCount(): Int {
        return listTransaction.size
    }

    fun setDataChart(data: ArrayList<DetailDataDonut>){
        this.listTransaction.clear()
        this.listTransaction.addAll(data)
        notifyDataSetChanged()
    }
}