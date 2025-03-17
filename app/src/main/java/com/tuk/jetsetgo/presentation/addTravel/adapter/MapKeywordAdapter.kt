package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemKeywordBinding

class MapKeywordAdapter(
    private val keywords: List<String>
) : RecyclerView.Adapter<MapKeywordAdapter.KeywordViewHolder>() {

    inner class KeywordViewHolder(private val binding: ItemKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: String) {
            binding.tvKeyword.text = keyword
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val binding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeywordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(keywords[position])
    }

    override fun getItemCount(): Int = keywords.size
}
