package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ItemChecklistBinding

class ChecklistItemAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<ChecklistItemAdapter.ChecklistViewHolder>() {

    inner class ChecklistViewHolder(private val binding: ItemChecklistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Log.d("ChecklistItemAdapter", "Adapter 바인딩 중: $item") // ✅ 아이템 하나하나 바인딩 되는지 확인

            binding.cbCheckItem.text = item
            binding.cbCheckItem.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                binding.cbCheckItem.buttonTintList =
                    ContextCompat.getColorStateList(binding.root.context,
                        if (isChecked) R.color.blue_main else R.color.gray_300
                    )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val binding = ItemChecklistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChecklistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
