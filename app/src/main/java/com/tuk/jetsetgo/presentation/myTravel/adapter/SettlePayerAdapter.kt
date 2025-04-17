package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ItemAddSpendCategoryBinding

class SettlePayerAdapter(
    private val payerList: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<SettlePayerAdapter.PayerViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class PayerViewHolder(val binding: ItemAddSpendCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.tvAddSpendCategory.text = name
            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onClick(name)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerViewHolder {
        val binding = ItemAddSpendCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PayerViewHolder(binding)
    }

    override fun getItemCount(): Int = payerList.size

    override fun onBindViewHolder(holder: PayerViewHolder, position: Int) {
        holder.bind(payerList[position])
        val isSelected = position == selectedPosition
        val backgroundRes = if (isSelected) R.drawable.shape_rect_999_blue_main_fill
        else R.drawable.shape_rect_999_gray300_fill
        holder.binding.tvAddSpendCategory.setBackgroundResource(backgroundRes)
    }

}
