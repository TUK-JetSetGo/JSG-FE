package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemScheduleBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ScheduleAdapter(
    private val onScheduleClick: () -> Unit
) : ListAdapter<ScheduleData, ScheduleAdapter.ScheduleViewHolder>(DiffCallback()) {

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleData: ScheduleData) {
            binding.tvScheduleTitle.text = scheduleData.title
            binding.tvScheduleTotalTime.text = scheduleData.totalTime
            binding.tvScheduleStart.text = formatToAmPmTime(scheduleData.startTime)
            binding.tvScheduleEnd.text = formatToAmPmTime(scheduleData.endTime)

            binding.root.setOnClickListener {
                onScheduleClick()
            }
        }

        private fun formatToAmPmTime(isoString: String): String {
            return try {
                val formatter = DateTimeFormatter.ISO_DATE_TIME
                val time = LocalDateTime.parse(isoString, formatter)
                time.format(DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN)) // 예: 오전 9:00, 오후 3:00
            } catch (e: Exception) {
                "시간 없음"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ScheduleData>() {
        override fun areItemsTheSame(oldItem: ScheduleData, newItem: ScheduleData): Boolean {
            return oldItem.title == newItem.title && oldItem.startTime == newItem.startTime
        }

        override fun areContentsTheSame(oldItem: ScheduleData, newItem: ScheduleData): Boolean {
            return oldItem == newItem
        }
    }
}

