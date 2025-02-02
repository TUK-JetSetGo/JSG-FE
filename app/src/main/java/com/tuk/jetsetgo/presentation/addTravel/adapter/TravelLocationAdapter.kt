package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemLocationBinding

class TravelLocationAdapter(
    private val locations: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TravelLocationAdapter.TravelLocationViewHolder>() {

    inner class TravelLocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: String, position: Int) {
            binding.tvAddTravelStartDate.text = location
            binding.ivAddTravelDelete.setOnClickListener {
                onDeleteClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelLocationViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelLocationViewHolder, position: Int) {
        holder.bind(locations[position], position)
    }

    override fun getItemCount(): Int = locations.size

    fun removeItem(position: Int) {
        locations.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, locations.size)
    }
}
