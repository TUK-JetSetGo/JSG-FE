package com.tuk.jetsetgo.presentation.review

import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentReviewBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.review.adapter.ReviewAdapter
import com.tuk.jetsetgo.presentation.review.adapter.ReviewData

class ReviewFragment: BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    private lateinit var reviewAdapter: ReviewAdapter

    private val reviewList = listOf(
        ReviewData("강릉 여행 추천지", "김기찬", listOf("https://image1.jpg", "https://image2.jpg"),"12","20","8",),
        ReviewData("서울 핫플", "김다희", listOf("https://image1.jpg", "https://image2.jpg", "https://image2.jpg"),"9","30","18",),
        ReviewData("해운대 맛집", "박준하", listOf("https://image1.jpg", "https://image2.jpg"),"15","19","9",),
        ReviewData("제주도 3박4일 후기", "정동훈", listOf("https://image1.jpg", "https://image2.jpg","https://image2.jpg"),"31","10","20",),
   )

    override fun initObserver() {
        initRecyclerView()
    }

    override fun initView() {

    }

    private fun initRecyclerView() {
        binding.rvTravelLocation.layoutManager = LinearLayoutManager(requireContext())
        reviewAdapter = ReviewAdapter(reviewList) {
            // findNavController().navigate(R.id.goToLocation)
        }
        binding.rvTravelLocation.adapter = reviewAdapter
    }
}