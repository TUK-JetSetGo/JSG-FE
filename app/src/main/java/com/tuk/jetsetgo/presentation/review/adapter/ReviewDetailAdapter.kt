package com.tuk.jetsetgo.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemReviewDetailBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapPictureAdapter

class ReviewDetailAdapter(
    initial: List<ReviewDetailData> = emptyList()
) : RecyclerView.Adapter<ReviewDetailAdapter.ReviewDetailViewHolder>() {

    private val items = initial.toMutableList()

    inner class ReviewDetailViewHolder(private val binding: ItemReviewDetailBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewDetailData) = with(binding) {
            tvReviewDetailTitle.text = data.dayTitle
            tvReviewDetailStar.text = data.rating
            tvReviewDetailContent.text = data.content
            val pictureAdapter = MapPictureAdapter(data.pictureList)
            rvReviewDetailPicture.adapter = pictureAdapter
            rvReviewDetailPicture.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewDetailViewHolder(ItemReviewDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ReviewDetailViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun submit(list: List<ReviewDetailData>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}
