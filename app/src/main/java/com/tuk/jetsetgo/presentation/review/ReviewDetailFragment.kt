package com.tuk.jetsetgo.presentation.review

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentReviewDetailBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.review.adapter.CommentAdapter
import com.tuk.jetsetgo.presentation.review.adapter.CommentData
import com.tuk.jetsetgo.presentation.review.adapter.ReviewDetailAdapter
import com.tuk.jetsetgo.presentation.review.adapter.ReviewDetailData

class ReviewDetailFragment: BaseFragment<FragmentReviewDetailBinding>(R.layout.fragment_review_detail) {
    private lateinit var reviewDetailAdapter: ReviewDetailAdapter
    private lateinit var commentAdapter: CommentAdapter

    private val reviewDetailList = listOf(
        ReviewDetailData("1일차 후기", listOf("https://image1.jpg", "https://image2.jpg"), "강문해변 산책하고 커피거리 카페 들렀어요."),
        ReviewDetailData("2일차 후기", listOf("https://image3.jpg", "https://image4.jpg"), "안목해변 가서 해수욕도 하고 맛집도 들렀어요."),
        ReviewDetailData("3일차 후기", listOf("https://image5.jpg"), "경포대에서 일몰 보고 감동했습니다.")
    )

    private val commentList = listOf(
        CommentData("김기찬", "너무 좋네요! 저도 가보고 싶어요."),
        CommentData("박준하", "후기 감사합니다! 사진도 예뻐요."),
        CommentData("정동훈", "가성비 최고인 듯합니다!")
    )

    override fun initObserver() {
        initReviewRecyclerView()
        initCommentRecyclerView()
    }

    override fun initView() {
        binding.ivReviewDetailBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initReviewRecyclerView() {
        reviewDetailAdapter = ReviewDetailAdapter(reviewDetailList)
        binding.rvReviewDetail.apply {
            adapter = reviewDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initCommentRecyclerView() {
        commentAdapter = CommentAdapter(commentList)
        binding.rvReviewDetailComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}