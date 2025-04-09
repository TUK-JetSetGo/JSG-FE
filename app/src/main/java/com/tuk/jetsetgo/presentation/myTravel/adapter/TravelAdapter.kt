package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemMyTravelBinding
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TravelAdapter(
    private val onTravelClick: (MyPlanResponseModel.MyTravelPlanInfoListModel) -> Unit
) : ListAdapter<MyPlanResponseModel.MyTravelPlanInfoListModel, TravelAdapter.TravelViewHolder>(DiffCallback()) {

    inner class TravelViewHolder(private val binding: ItemMyTravelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(travelData: MyPlanResponseModel.MyTravelPlanInfoListModel) {
            binding.tvMyTravelPlace.text = ""
            binding.tvMyTravelDuration.text = travelData.travelDuration
            binding.tvMyTravelDate.text = "${travelData.travelStartDate} ~ ${travelData.travelEndDate}"
            try {
                val formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd") // travelStartDate 형식 확인 필요
                val today = LocalDate.now()
                val startDate = LocalDate.parse(travelData.travelStartDate, formatter)
                val endDate = LocalDate.parse(travelData.travelEndDate, formatter)

                val status = when {
                    today.isBefore(startDate) -> "진행 전"
                    today.isAfter(endDate) -> "완료"
                    else -> "진행 중"
                }

                binding.tvMyTravelState.text = status
            } catch (e: Exception) {
                binding.tvMyTravelState.text = "날짜 오류"
                Log.e("TravelViewHolder", "날짜 파싱 오류", e)
            }

            binding.root.setOnClickListener {
                onTravelClick(travelData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemMyTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<MyPlanResponseModel.MyTravelPlanInfoListModel>() {
        override fun areItemsTheSame(oldItem: MyPlanResponseModel.MyTravelPlanInfoListModel, newItem: MyPlanResponseModel.MyTravelPlanInfoListModel): Boolean {
            return oldItem.travelPlanId == newItem.travelPlanId
        }

        override fun areContentsTheSame(oldItem: MyPlanResponseModel.MyTravelPlanInfoListModel, newItem: MyPlanResponseModel.MyTravelPlanInfoListModel): Boolean {
            return oldItem == newItem
        }
    }
}
