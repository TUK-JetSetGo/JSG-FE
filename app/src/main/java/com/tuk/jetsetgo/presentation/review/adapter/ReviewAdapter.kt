package com.tuk.jetsetgo.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemReviewBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapPictureAdapter

class ReviewAdapter(
    private val reviews: List<ReviewData>,
    private val onItemClick: (ReviewData) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reviewData: ReviewData) {
            binding.tvReviewTitle.text = reviewData.title
            binding.tvReviewNickname.text = reviewData.nickname
            binding.tvReviewLike.text = reviewData.like
            binding.tvReviewComment.text = reviewData.comment
            binding.tvReviewBookmark.text = reviewData.bookmark

            // 사진 RecyclerView 설정
            val mapPictureAdapter = MapPictureAdapter(reviewData.picture)
            binding.rvReviewPicture.adapter = mapPictureAdapter
            binding.rvReviewPicture.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            // 클릭 리스너 추가
            binding.root.setOnClickListener {
                onItemClick(reviewData)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size
}
