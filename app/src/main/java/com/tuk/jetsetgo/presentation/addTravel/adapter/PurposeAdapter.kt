package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemBottomSheetBinding
import com.tuk.jetsetgo.domain.model.response.PurposeResponseModel

class PurposeAdapter(private val onItemClick: (PurposeResponseModel.TravelPurposeInfoListModel) -> Unit) :
    RecyclerView.Adapter<PurposeAdapter.PurposeViewHolder>() {

    private var items: List<PurposeResponseModel.TravelPurposeInfoListModel> = emptyList()

    inner class PurposeViewHolder(private val binding: ItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(purpose: PurposeResponseModel.TravelPurposeInfoListModel) {
            binding.tvBottomSheetItem.text = purpose.name
            binding.root.setOnClickListener { onItemClick(purpose) } // 아이템 클릭 이벤트
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeViewHolder {
        val binding = ItemBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PurposeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurposeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<PurposeResponseModel.TravelPurposeInfoListModel>) {
        items = newList
        notifyDataSetChanged() // 데이터 갱신
    }
}
