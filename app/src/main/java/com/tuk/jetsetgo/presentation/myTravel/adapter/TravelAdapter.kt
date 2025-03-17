package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemMyTravelBinding

class TravelAdapter(
    private val travels: List<TravelData>,
    private val onTravelClick: () -> Unit
) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    inner class TravelViewHolder(private val binding: ItemMyTravelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(travelData: TravelData) {
            binding.tvMyTravelPlace.text = travelData.place
            binding.tvMyTravelDuration.text = travelData.duration
            binding.tvMyTravelDate.text = travelData.date
            binding.tvMyTravelState.text = travelData.state
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemMyTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(travels[position])
    }

    override fun getItemCount(): Int = travels.size
}
