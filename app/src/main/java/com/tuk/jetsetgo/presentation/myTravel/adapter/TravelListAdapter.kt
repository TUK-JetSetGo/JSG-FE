package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemDirectionsBinding


class TravelListAdapter(
    private val onStartClick: (ScheduleData) -> Unit,
    private val onEndClick: (ScheduleData) -> Unit
): ListAdapter<ScheduleData, TravelListAdapter.TravelViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemDirectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TravelViewHolder(private val binding: ItemDirectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleData: ScheduleData) {
            binding.tvScheduleTitle.text = scheduleData.title
            binding.ivStartPoint.setOnClickListener {
                onStartClick(scheduleData)
            }
            binding.ivEndPoint.setOnClickListener {
                onEndClick(scheduleData)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ScheduleData>() {
        override fun areItemsTheSame(oldItem: ScheduleData, newItem: ScheduleData): Boolean {
            return oldItem.touristSpotId == newItem.touristSpotId
        }

        override fun areContentsTheSame(oldItem: ScheduleData, newItem: ScheduleData): Boolean {
            return oldItem == newItem
        }
    }
}