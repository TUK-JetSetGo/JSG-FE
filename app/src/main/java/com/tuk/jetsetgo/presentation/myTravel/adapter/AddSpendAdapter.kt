package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemAddSpendCategoryBinding
import com.tuk.jetsetgo.databinding.ItemSpendBinding

class AddSpendAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<AddSpendAdapter.AddSpendViewHolder>() {

    private val selectedPositions = mutableSetOf<Int>()

    inner class AddSpendViewHolder(
        private val binding: ItemAddSpendCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, position: Int) {
            binding.tvAddSpendCategory.text = name

            val isSelected = selectedPositions.contains(position)
            val backgroundRes = if (isSelected) {
                com.tuk.jetsetgo.R.drawable.shape_rect_20_blue_main_fill
            } else {
                com.tuk.jetsetgo.R.drawable.shape_rect_999_gray300_fill
            }
            binding.tvAddSpendCategory.setBackgroundResource(backgroundRes)

            binding.root.setOnClickListener {
                if (selectedPositions.contains(position)) {
                    selectedPositions.remove(position)
                } else {
                    selectedPositions.add(position)
                }
                notifyItemChanged(position)
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
