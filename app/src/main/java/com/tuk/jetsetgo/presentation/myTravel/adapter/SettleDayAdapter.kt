package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ItemSettleBinding

class SettleDayAdapter(
    private val context: Context,
    private val dayMap: Map<String, List<String>>
) : RecyclerView.Adapter<SettleDayAdapter.DayViewHolder>() {

    inner class DayViewHolder(val binding: ItemSettleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String, list: List<String>) {
            binding.tvSettleTitle.text = title
            val totalPrice = list.sumOf {
                it.substringAfter(",")
                    .replace("[^0-9]".toRegex(), "")
                    .toInt()
            }
            binding.tvSettleTotalPrice.text = "${String.format("%,d", totalPrice)}원"
            val adapter = SettleListAdapter(list)
            binding.rvSettleList.layoutManager = LinearLayoutManager(context)
            binding.rvSettleList.adapter = adapter
            binding.rvSettleList.visibility = View.GONE

            binding.viewSettleBtn.setOnClickListener {
                val isVisible = binding.rvSettleList.visibility == View.VISIBLE
                binding.rvSettleList.visibility = if (isVisible) View.GONE else View.VISIBLE
                binding.viewSettleBtn.setBackgroundResource(
                    if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemSettleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    override fun getItemCount(): Int = dayMap.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val key = dayMap.keys.toList()[position]
        holder.bind(key, dayMap[key] ?: emptyList())
    }
}
