package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto
import com.tuk.jetsetgo.databinding.ItemRouteListBinding

class RouteListAdapter(

): ListAdapter<RouteItem, RouteListAdapter.RouteListViewHodler>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteListViewHodler {
        val binding = ItemRouteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RouteListViewHodler(binding)
    }

    override fun onBindViewHolder(holder: RouteListViewHodler, position: Int) {
        holder.bind(getItem(position))

    }

    inner class RouteListViewHodler(
        private val binding: ItemRouteListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val detailAdapter = RouteDetailAdapter()

        fun bind(item: RouteItem) = with(binding) {
            if (item.label == RouteLabel.FASTEST) {
                tvRouteTitle.text = "가장 빠른 경로"
                tvRouteTitle.visibility = View.VISIBLE
            } else {
                tvRouteTitle.text = ""
                tvRouteTitle.visibility = View.GONE   // 또는 GONE
            }


            // 2) 총 소요시간
            tvRouteTime.text = "${item.path.info.totalTime}분"

            // 3) 버스 번호 (첫 번째 버스 구간의 첫 노선)
            val firstBusLeg = item.path.subPath.firstOrNull { it.trafficType == 2 }
            val busNo = firstBusLeg?.lane?.firstOrNull()?.busNo
            tvBusNumber.text = busNo ?: ""

            // 필요하면: 도보 정보 표시
            tvRouteWalkTime.text = "총 도보 ${item.path.info.totalWalk}m"

            if (rvRouteDetail.adapter == null) {
                rvRouteDetail.layoutManager = LinearLayoutManager(itemView.context)
                rvRouteDetail.adapter = detailAdapter
            }

            detailAdapter.submitList(item.path.toDetailItems())


            // 펼침/접힘 상태 적용
            applyVisibility(item.isExpanded, item.path)

            // 클릭 리스너 (상세보기/화살표 => 펼치기)
            val expandClick = View.OnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION && !item.isExpanded) {
                    item.isExpanded = true
                    notifyItemChanged(bindingAdapterPosition)
                }
            }

            tvRouteDetail.setOnClickListener(expandClick)
            ivRouteDetail.setOnClickListener(expandClick)

            // 간단히 => 접기
            tvRouteSimple.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION && item.isExpanded) {
                    item.isExpanded = false
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }

        fun applyVisibility(expanded: Boolean, path: OdsayRouteDto.ResultDto.PathDto) = with(binding) {
            if (expanded) {
                tvRouteDetail.visibility = View.GONE
                ivRouteDetail.visibility = View.GONE
                tvRouteInfo.visibility = View.VISIBLE
                rvRouteDetail.visibility = View.VISIBLE
                tvRouteSimple.visibility = View.VISIBLE
            } else {
                tvRouteDetail.visibility = View.VISIBLE
                ivRouteDetail.visibility = View.VISIBLE
                tvRouteInfo.visibility = View.GONE
                rvRouteDetail.visibility = View.GONE
                tvRouteSimple.visibility = View.GONE
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<RouteItem>() {
        override fun areItemsTheSame(oldItem: RouteItem, newItem: RouteItem): Boolean =
            oldItem.path.info.mapObj == newItem.path.info.mapObj


        override fun areContentsTheSame(oldItem: RouteItem, newItem: RouteItem): Boolean {
            return oldItem == newItem
        }
    }
}