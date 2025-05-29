package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ItemScheduleBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ScheduleAdapter(
    private val mode: ScheduleMode,
    private val onScheduleClick: (ScheduleData) -> Unit = {},
    private val onAddClick: (ScheduleData) -> Unit = {},
    private val onEditClick: (ScheduleData) -> Unit = {},
    private val onDeleteClick: (ScheduleData) -> Unit = {}
) : ListAdapter<ScheduleData, ScheduleAdapter.ScheduleViewHolder>(DiffCallback()) {

    enum class ScheduleMode {
        SELECTABLE, // ModifyScheduleFragment 전용: 클릭해서 선택만 가능
        EDITABLE    // DetailScheduleFragment 전용: PopupMenu 사용
    }

    private val selectedItems = mutableSetOf<ScheduleData>()

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(scheduleData: ScheduleData) {
            binding.tvScheduleTitle.text = scheduleData.title
            binding.tvScheduleTotalTime.text = scheduleData.totalTime
            binding.tvScheduleStart.text = formatToAmPmTime(scheduleData.startTime)
            binding.tvScheduleEnd.text = formatToAmPmTime(scheduleData.endTime)

            when (mode) {
                ScheduleMode.EDITABLE -> {
                    binding.viewDotSeeMore.visibility = View.VISIBLE
                    binding.clSchedule.setBackgroundResource(R.drawable.shape_rect_10_gray300_fill)

                    binding.viewDotSeeMore.setOnClickListener { anchor ->
                        val popup = PopupMenu(anchor.context, anchor, Gravity.START, 0, R.style.WhitePopupMenu)
                        popup.menuInflater.inflate(R.menu.menu_edit_schedule, popup.menu)
                        popup.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.menu_add -> {
                                    onAddClick(scheduleData)
                                    true
                                }
                                R.id.menu_edit -> {
                                    onEditClick(scheduleData)
                                    true
                                }
                                R.id.menu_delete -> {
                                    onDeleteClick(scheduleData)
                                    true
                                }
                                else -> false
                            }
                        }
                        popup.show()
                    }
                    binding.root.setOnClickListener {
                        if (selectedItems.contains(scheduleData)) {
                            selectedItems.remove(scheduleData)
                        } else {
                            selectedItems.add(scheduleData)
                        }

                        // ✅ 선택된 항목 로그 출력
                        Log.d("ScheduleAdapter", "선택된 일정 목록: ${selectedItems.map { it.title + " (${it.orderIndex})" }}")

                        notifyItemChanged(bindingAdapterPosition)
                        onScheduleClick(scheduleData)
                    }

                }

                ScheduleMode.SELECTABLE -> {
                    binding.viewDotSeeMore.visibility = View.INVISIBLE

                    val isSelected = selectedItems.contains(scheduleData)
                    binding.clSchedule.setBackgroundResource(
                        if (isSelected) R.drawable.shape_rect_10_blue_fill
                        else R.drawable.shape_rect_10_gray300_fill
                    )

                    binding.root.setOnClickListener {
                        if (selectedItems.contains(scheduleData)) {
                            selectedItems.remove(scheduleData)
                        } else {
                            selectedItems.add(scheduleData)
                        }
                        notifyItemChanged(bindingAdapterPosition)
                        onScheduleClick(scheduleData)
                    }
                }

            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun formatToAmPmTime(isoString: String): String {
            return try {
                val formatter = DateTimeFormatter.ISO_DATE_TIME
                val time = LocalDateTime.parse(isoString, formatter)
                time.format(DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN))
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

    fun clearSelections() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<ScheduleData> {
        return selectedItems.toList()
    }

}


