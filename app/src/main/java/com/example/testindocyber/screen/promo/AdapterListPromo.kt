package com.example.testindocyber.screen.promo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testindocyber.databinding.ItemPromoBinding
import com.example.testindocyber.model.network.ResponsePromoItem

class AdapterListPromo(
    private val context: Context,
    private val listenerItemPromo: (ResponsePromoItem) -> Unit
) : RecyclerView.Adapter<AdapterListPromo.ViewHolder>() {

    private val listPromo = mutableListOf<ResponsePromoItem>()

    inner class ViewHolder(val view: ItemPromoBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: ResponsePromoItem) {
            Glide.with(context)
                .load(data.img?.url)
                .into(view.ivPromo)

            view.tvCount.text = data.nama

            itemView.setOnClickListener {
                listenerItemPromo(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemPromoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPromo[position])
    }

    override fun getItemCount(): Int {
        return listPromo.size
    }

    fun setDataPromo(data: ArrayList<ResponsePromoItem>){
        this.listPromo.clear()
        this.listPromo.addAll(data)
        notifyDataSetChanged()
    }
}