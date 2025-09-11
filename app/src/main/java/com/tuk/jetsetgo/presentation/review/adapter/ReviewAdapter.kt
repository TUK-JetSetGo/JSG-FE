package com.tuk.jetsetgo.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemReviewBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapPictureAdapter

class ReviewAdapter(
    private val onItemClick: (ReviewData) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val reviews = mutableListOf<ReviewData>()  // 내부 보관용

    fun submit(list: List<ReviewData>) {
        reviews.clear()
        reviews.addAll(list)
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData: ReviewData) = with(binding) {
            tvReviewTitle.text = reviewData.title
            tvReviewNickname.text = reviewData.nickname
            tvReviewLike.text = reviewData.like
            tvReviewComment.text = reviewData.comment
            tvReviewBookmark.text = reviewData.bookmark

            rvReviewPicture.apply {
                adapter = MapPictureAdapter(reviewData.picture)
                layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            }

            root.setOnClickListener { onItemClick(reviewData) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) =
        holder.bind(reviews[position])

    override fun getItemCount(): Int = reviews.size
}

