package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ItemChecklistBinding
import com.tuk.jetsetgo.domain.model.response.myTravel.GetCheckListResponseModel

class ChecklistItemAdapter(
    private val items: List<GetCheckListResponseModel>,
    private val onDeleteClick: (GetCheckListResponseModel) -> Unit,
    private val onCheckChanged: (GetCheckListResponseModel, Boolean) -> Unit
) : RecyclerView.Adapter<ChecklistItemAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemChecklistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetCheckListResponseModel) {
            binding.cbCheckItem.text = item.itemName
            binding.cbCheckItem.isChecked = item.isChecked

            binding.cbCheckItem.setOnCheckedChangeListener(null) // 상태 리스너 초기화 (재활용 방지)
            binding.cbCheckItem.setOnCheckedChangeListener { _, isChecked ->
                Log.d("ChecklistAdapter", "☑️ 체크 변경: ${item.itemName} → $isChecked")
                onCheckChanged(item, isChecked)
            }

            binding.clLocationDelete.setOnClickListener {
                Log.d("ChecklistAdapter", "🗑️ 삭제 클릭됨: ${item.itemName}")
                onDeleteClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChecklistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

