package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemStartPointBinding

class StartPointAdapter(
    private val itemCount: Int,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<StartPointAdapter.StartPointViewHolder>() {

    private var startPointNames: List<String> = emptyList()

    fun submitStartPointNames(names: List<String>) {
        startPointNames = names
        notifyDataSetChanged()
    }

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

            // startPointName이 있으면 보여주고, 없으면 빈칸
            binding.tvStartPointLocation.text = startPointNames.getOrNull(position) ?: ""

            // 클릭 시 Map으로 이동
            binding.root.setOnClickListener {
                onItemClick(position)
            }
        }
    }
}


