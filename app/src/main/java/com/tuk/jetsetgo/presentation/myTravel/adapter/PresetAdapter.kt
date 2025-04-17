package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ItemCategoryBinding

class PresetAdapter(
    private val items: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<PresetAdapter.PresetViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class PresetViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, position: Int) {
            binding.tvAddSpendCategory.text = item

            // 선택된 항목과 그렇지 않은 항목 구분하여 배경 적용
            val backgroundRes = if (position == selectedPosition) {
                R.drawable.shape_rect_999_blue_main_fill
            } else {
                R.drawable.shape_rect_999_gray300_fill
            }
            binding.tvAddSpendCategory.setBackgroundResource(backgroundRes)

            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(position)
                Log.d("PresetAdapter", "프리셋 클릭됨: $item")
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresetViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PresetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PresetViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}
