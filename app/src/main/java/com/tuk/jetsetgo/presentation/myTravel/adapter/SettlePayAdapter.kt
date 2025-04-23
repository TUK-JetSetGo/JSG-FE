package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemSettlePayBinding

class SettlePayAdapter(
    private var payList: List<String>
) : RecyclerView.Adapter<SettlePayAdapter.PayViewHolder>() {

    inner class PayViewHolder(val binding: ItemSettlePayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.tvSettlePayTitle.text = data.substringBefore("에게") + "에게"
            binding.tvSettleTotalPayPrice.text = data.substringAfter("에게")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayViewHolder {
        val binding = ItemSettlePayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PayViewHolder(binding)
    }

    override fun getItemCount(): Int = payList.size

    override fun onBindViewHolder(holder: PayViewHolder, position: Int) {
        holder.bind(payList[position])
    }

    fun updateList(newList: List<String>) {
        payList = newList
        notifyDataSetChanged()
    }
}

