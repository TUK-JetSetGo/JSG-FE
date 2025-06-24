package com.tuk.jetsetgo.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemReviewDetailBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapPictureAdapter

class ReviewDetailAdapter(
    private val detailList: List<ReviewDetailData>
) : RecyclerView.Adapter<ReviewDetailAdapter.ReviewDetailViewHolder>() {

    inner class ReviewDetailViewHolder(private val binding: ItemReviewDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ReviewDetailData) {
            binding.tvReviewDetailTitle.text = data.dayTitle
            binding.tvReviewDetailContent.text = data.content

            val pictureAdapter = MapPictureAdapter(data.pictureList)
            binding.rvReviewDetailPicture.apply {
                adapter = pictureAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewDetailViewHolder {
        val binding = ItemReviewDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewDetailViewHolder, position: Int) {
        holder.bind(detailList[position])
    }

    override fun getItemCount(): Int = detailList.size
}
