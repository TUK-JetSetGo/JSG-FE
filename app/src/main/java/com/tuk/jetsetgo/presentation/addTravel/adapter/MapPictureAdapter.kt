package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuk.jetsetgo.databinding.ItemMapPictureBinding

class MapPictureAdapter(
    private val pictures: List<String> // URL 또는 로컬 이미지 경로 리스트
) : RecyclerView.Adapter<MapPictureAdapter.PictureViewHolder>() {

    inner class PictureViewHolder(private val binding: ItemMapPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(picture: String) {
            // 예제: Glide를 사용하여 이미지 로드
            Glide.with(binding.ivPicture.context)
                .load(picture)
                .into(binding.ivPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding = ItemMapPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(pictures[position])
    }

    override fun getItemCount(): Int = pictures.size
}
