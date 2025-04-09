package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemBottomSheetBinding

class TransportationAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<TransportationAdapter.ViewHolder>() {

    private val items = mutableListOf<String>()

    fun updateList(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvBottomSheetItem.text = item
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
