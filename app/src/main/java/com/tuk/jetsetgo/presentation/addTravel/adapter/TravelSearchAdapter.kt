package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemLastSearchBinding

class TravelSearchAdapter(
    private val searches: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TravelSearchAdapter.TravelSearchViewHolder>() {

    inner class TravelSearchViewHolder(private val binding: ItemLastSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(search: String, position: Int) {
            binding.ivLastSearchName.text = search
            binding.ivLastSearchDelete.setOnClickListener {
                onDeleteClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelSearchViewHolder {
        val binding = ItemLastSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelSearchViewHolder, position: Int) {
        holder.bind(searches[position], position)
    }

    override fun getItemCount(): Int = searches.size

    fun removeItem(position: Int) {
        searches.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, searches.size)
    }
}
