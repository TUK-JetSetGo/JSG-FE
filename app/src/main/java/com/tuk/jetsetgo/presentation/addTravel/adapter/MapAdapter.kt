package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemMapBinding
import com.tuk.jetsetgo.domain.model.response.addTravel.SpotInfoResponseModel

class MapAdapter(
    private val onItemClick: (SpotInfoResponseModel.TouristSpotInfoListModel) -> Unit,
    private val onAddButtonClick: (SpotInfoResponseModel.TouristSpotInfoListModel) -> Unit
): ListAdapter<SpotInfoResponseModel.TouristSpotInfoListModel, MapAdapter.MapViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val binding = ItemMapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MapViewHolder(private val binding: ItemMapBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val categoryAdapter = CategoryAdapter()
        private val thumbnailAdapter = ThumbnailAdapter()
        init {
            binding.rvMapKeyword.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = categoryAdapter
            }
            binding.rvMapPicture.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = thumbnailAdapter
            }
        }

        fun bind(item: SpotInfoResponseModel.TouristSpotInfoListModel) {
            binding.tvMapName.text = item.name
            binding.tvMapAddress.text = item.address

            // 키워드 RecyclerView 설정
            val categories = item.category?.replace("[", "")?.replace("]", "")?.split(",")?.map { it.trim().replace("\"", "") }
            categoryAdapter.submitList(categories)

            // 사진 RecyclerView 설정
            val thumbnails = item.thumbnailUrls?.replace("[", "")?.replace("]", "")?.split(",")?.map { it.trim().replace("\"", "") } ?: emptyList()
            thumbnailAdapter.submitList(thumbnails)

            binding.root.setOnClickListener {
                onItemClick(item)
            }

            // 추가 버튼 클릭 리스너 설정
            binding.viewMapAddBtn.setOnClickListener {
                onAddButtonClick(item)
            }
        }
    }
    class DiffCallback : DiffUtil.ItemCallback<SpotInfoResponseModel.TouristSpotInfoListModel>() {
        override fun areItemsTheSame(oldItem: SpotInfoResponseModel.TouristSpotInfoListModel, newItem: SpotInfoResponseModel.TouristSpotInfoListModel): Boolean {
            return oldItem.touristSpotId == newItem.touristSpotId
        }

        override fun areContentsTheSame(oldItem: SpotInfoResponseModel.TouristSpotInfoListModel, newItem: SpotInfoResponseModel.TouristSpotInfoListModel): Boolean {
            return oldItem == newItem
        }
    }
}