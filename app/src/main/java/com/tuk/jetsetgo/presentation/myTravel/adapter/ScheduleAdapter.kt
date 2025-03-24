package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private var schedules: List<ScheduleData>,  // 🔁 var로 변경
    private val onScheduleClick: () -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleData: ScheduleData) {
            binding.tvScheduleTitle.text = scheduleData.title
            binding.tvScheduleTotalTime.text = scheduleData.totalTime
            binding.tvScheduleStart.text = scheduleData.startTime
            binding.tvScheduleEnd.text = scheduleData.endTime

            binding.root.setOnClickListener {
                onScheduleClick()
            }
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

    // 리스트 갱신용 함수
    fun updateList(newSchedules: List<ScheduleData>) {
        this.schedules = newSchedules
        notifyDataSetChanged()
    }
}

