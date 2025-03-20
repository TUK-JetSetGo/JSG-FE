package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemMyTravelBinding
import com.tuk.jetsetgo.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val schedules: List<ScheduleData>,
    private val onScheduleClick: () -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleData: ScheduleData) {
            binding.tvScheduleTitle.text = scheduleData.title
            binding.tvScheduleTotalTime.text = scheduleData.totalTime
            binding.tvScheduleStart.text = scheduleData.startTime
            binding.tvScheduleEnd.text = scheduleData.endTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount(): Int = schedules.size
}
