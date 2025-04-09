package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ItemAddSpendCategoryBinding
import com.tuk.jetsetgo.databinding.ItemSpendBinding

class AddSpendAdapter(
    private val items: List<String>,
    private val singleSelection: Boolean = false // default = 다중선택, true면 단일선택
) : RecyclerView.Adapter<AddSpendAdapter.AddSpendViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private val selectedPositions = mutableSetOf<Int>() // 다중선택용

    inner class AddSpendViewHolder(
        private val binding: ItemAddSpendCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, position: Int) {
            binding.tvAddSpendCategory.text = name

            val isSelected = if (singleSelection) {
                position == selectedPosition
            } else {
                selectedPositions.contains(position)
            }

            val backgroundRes = if (isSelected) {
                R.drawable.shape_rect_20_blue_main_fill
            } else {
                R.drawable.shape_rect_999_gray300_fill
            }
            binding.tvAddSpendCategory.setBackgroundResource(backgroundRes)

            binding.root.setOnClickListener {
                if (singleSelection) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(position)
                } else {
                    if (selectedPositions.contains(position)) {
                        selectedPositions.remove(position)
                    } else {
                        selectedPositions.add(position)
                    }
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSpendViewHolder {
        val binding = ItemAddSpendCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddSpendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddSpendViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}

