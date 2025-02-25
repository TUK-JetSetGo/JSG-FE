package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemBottomSheetBinding
import com.tuk.jetsetgo.domain.model.response.ThemesResponseModel

class ThemeAdapter(private val onItemClick: (ThemesResponseModel.TravelThemeInfoListModel) -> Unit) :
    RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    private var items: List<ThemesResponseModel.TravelThemeInfoListModel> = emptyList()

    inner class ThemeViewHolder(private val binding: ItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theme: ThemesResponseModel.TravelThemeInfoListModel) {
            binding.tvBottomSheetItem.text = theme.name
            binding.root.setOnClickListener { onItemClick(theme) } // 아이템 클릭 이벤트
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val binding = ItemBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<ThemesResponseModel.TravelThemeInfoListModel>) {
        items = newList
        notifyDataSetChanged() // 데이터 갱신
    }
}