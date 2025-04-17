package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemAddSpendCategoryBinding
import com.tuk.jetsetgo.databinding.ItemSettleListBinding

class SettleListAdapter(
    private val itemList: List<String>
) : RecyclerView.Adapter<SettleListAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ItemSettleListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.tvSettleTitle.text = data.substringBefore(",")
            binding.tvSettleTotalPrice.text = data.substringAfter(",").trim()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSettleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}
