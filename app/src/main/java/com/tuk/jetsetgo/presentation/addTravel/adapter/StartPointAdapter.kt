package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemStartPointBinding

class StartPointAdapter(
    private val itemCount: Int,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<StartPointAdapter.StartPointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartPointViewHolder {
        val binding = ItemStartPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StartPointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StartPointViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemCount

    inner class StartPointViewHolder(
        private val binding: ItemStartPointBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val day = position + 1
            binding.tvStartPointDay.text = "${day}일차"

            // 아이템 전체에 클릭 리스너 설정
            binding.root.setOnClickListener {
                onItemClick(position)
            }
        }
    }
}

