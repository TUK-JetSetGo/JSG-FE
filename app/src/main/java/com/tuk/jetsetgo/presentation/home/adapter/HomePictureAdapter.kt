package com.tuk.jetsetgo.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemHomePictureBinding

class HomePictureAdapter(private val items: List<HomePictureItem>) :
    RecyclerView.Adapter<HomePictureAdapter.HomePictureViewHolder>() {

    inner class HomePictureViewHolder(private val binding: ItemHomePictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomePictureItem) {
            binding.ivHomePicture.setImageResource(item.imageResId)
            binding.tvHomeComment.text = item.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePictureViewHolder {
        val binding = ItemHomePictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePictureViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
