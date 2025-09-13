package com.tuk.jetsetgo.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemReviewBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapPictureAdapter

class ReviewAdapter(
    private val onItemClick: (ReviewData, Int) -> Unit   // ✅ position 함께 전달
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val reviews = mutableListOf<ReviewData>()

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

            root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(reviews[pos], pos)   // ✅ 아이템 + position
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) =
        holder.bind(reviews[position])

    override fun getItemCount(): Int = reviews.size
}
