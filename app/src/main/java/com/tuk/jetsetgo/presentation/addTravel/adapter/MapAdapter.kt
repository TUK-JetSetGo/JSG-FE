package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemMapBinding

class MapAdapter(
    private val maps: List<MapData>,  // 기존 String 리스트 → 데이터 클래스 사용
    private val onAddButtonClick: () -> Unit
) : RecyclerView.Adapter<MapAdapter.TravelMapViewHolder>() {

    inner class TravelMapViewHolder(private val binding: ItemMapBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mapData: MapData) {
            binding.tvMapName.text = mapData.name

            // 키워드 RecyclerView 설정
            val keywordAdapter = KeywordAdapter(mapData.keywords)
            binding.rvMapKeyword.adapter = keywordAdapter
            binding.rvMapKeyword.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            // 사진 RecyclerView 설정
            val pictureAdapter = PictureAdapter(mapData.pictures)
            binding.rvMapPicture.adapter = pictureAdapter
            binding.rvMapPicture.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            // 추가 버튼 클릭 리스너 설정
            binding.viewMapAddBtn.setOnClickListener {
                onAddButtonClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelMapViewHolder {
        val binding = ItemMapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelMapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelMapViewHolder, position: Int) {
        holder.bind(maps[position])
    }

    override fun getItemCount(): Int = maps.size
}
