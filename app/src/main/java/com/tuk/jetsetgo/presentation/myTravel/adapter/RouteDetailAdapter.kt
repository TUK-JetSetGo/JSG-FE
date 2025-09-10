package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemRouteDetailBinding

class RouteDetailAdapter(

): ListAdapter<RouteDetailItem, RouteDetailAdapter.RouteDetailViewHodler>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteDetailViewHodler {
        val binding = ItemRouteDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RouteDetailViewHodler(binding)
    }

    override fun onBindViewHolder(holder: RouteDetailViewHodler, position: Int) {
        holder.bind(getItem(position))

    }

    inner class RouteDetailViewHodler(
        private val binding: ItemRouteDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RouteDetailItem) = with(binding) {
            tvRouteDetailTitle.text = item.title
            tvRoutePlace.text = item.place
            tvRouteTime.text = item.timeText

            // 아이콘 있으면
            val iconView = runCatching { ivRouteIcon }.getOrNull()
            iconView?.setImageResource(item.iconRes)

        }

    }

    class DiffCallback : DiffUtil.ItemCallback<RouteDetailItem>() {
        override fun areItemsTheSame(oldItem: RouteDetailItem, newItem: RouteDetailItem): Boolean =
            oldItem.id  == newItem.id


        override fun areContentsTheSame(oldItem: RouteDetailItem, newItem: RouteDetailItem): Boolean {
            return oldItem == newItem
        }
    }
}