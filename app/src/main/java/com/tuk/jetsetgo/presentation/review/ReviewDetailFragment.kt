package com.tuk.jetsetgo.presentation.review

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentReviewDetailBinding
import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.review.ReviewViewModel
import com.tuk.jetsetgo.presentation.review.adapter.CommentAdapter
import com.tuk.jetsetgo.presentation.review.adapter.CommentData
import com.tuk.jetsetgo.presentation.review.adapter.ReviewDetailAdapter
import com.tuk.jetsetgo.presentation.review.adapter.ReviewDetailData
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewDetailFragment : BaseFragment<FragmentReviewDetailBinding>(R.layout.fragment_review_detail) {

    private val viewModel: ReviewViewModel by viewModels()
    private val args: ReviewDetailFragmentArgs by navArgs()

    private lateinit var reviewDetailAdapter: ReviewDetailAdapter
    private lateinit var commentAdapter: CommentAdapter

    private var isLiked = false
    private var isBookmarked = false

    override fun initObserver() {
        // 상세 섹션 RV
        reviewDetailAdapter = ReviewDetailAdapter(emptyList())
        binding.rvReviewDetail.apply {
            adapter = reviewDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

        // 댓글(서버 스펙에 없음 → 일단 비움)
        commentAdapter = CommentAdapter(mutableListOf())
        binding.rvReviewDetailComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
        }

        // 리뷰 조회 구독
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getReviewState.collect { state ->
                    when (state) {
                        is UiState.Loading -> { /* 로딩 처리 필요시 */ }
                        is UiState.Success -> render(state.data)
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), "리뷰 조회 실패", Toast.LENGTH_SHORT).show()
                            Log.e("ReviewDetailFragment", "getReview error", state.error)
                        }
                        UiState.Empty -> Unit
                    }
                }
            }
        }

        // 호출
        viewModel.getReview(args.travelPlanId)
    }

    override fun initView() = with(binding) {
        // 제목(응답에 타이틀이 없다면 넘어온 값으로 표기)
        // tvReviewDetailTitle.text = args.title ?: tvReviewDetailTitle.text

        ivReviewDetailBack.setOnClickListener { findNavController().popBackStack() }

        // 좋아요/북마크 UI는 기존 목업 로직 유지
        viewReviewDetailLike.setOnClickListener {
            val current = tvReviewDetailLike.text.toString().toIntOrNull() ?: 0
            if (isLiked) {
                viewReviewDetailLike.setBackgroundResource(R.drawable.ic_like)
                tvReviewDetailLike.text = (current - 1).coerceAtLeast(0).toString()
            } else {
                viewReviewDetailLike.setBackgroundResource(R.drawable.ic_like_sel)
                tvReviewDetailLike.text = (current + 1).toString()
            }
            isLiked = !isLiked
        }

        viewReviewDetailBookmark.setOnClickListener {
            val current = tvReviewDetailBookmark.text.toString().toIntOrNull() ?: 0
            if (isBookmarked) {
                viewReviewDetailBookmark.setBackgroundResource(R.drawable.ic_bookmark)
                tvReviewDetailBookmark.text = (current - 1).coerceAtLeast(0).toString()
            } else {
                viewReviewDetailBookmark.setBackgroundResource(R.drawable.ic_bookmark_sel)
                tvReviewDetailBookmark.text = (current + 1).toString()
            }
            isBookmarked = !isBookmarked
        }

        // 댓글 등록(목업 유지)
        tvReviewDetailAddCommentBtn.setOnClickListener {
            val text = etReviewDetailAddComment.text?.toString()?.trim().orEmpty()
            if (text.isNotEmpty()) {
                commentAdapter.addComment(CommentData("나", text))
                etReviewDetailAddComment.setText("")
                rvReviewDetailComment.smoothScrollToPosition(commentAdapter.itemCount - 1)
                val current = tvReviewDetailComment.text.toString().toIntOrNull() ?: 0
                tvReviewDetailComment.text = (current + 1).toString()
            }
        }
    }

    private fun render(data: GetReviewResponseModel) = with(binding) {
        // 헤더
        tvReviewDetailStar.text = format1(data.overallReviewInfo.rating)
        tvReviewDetailTotalReviewContent.text = data.overallReviewInfo.content

        // 상세 리스트
        val uiList = data.dailyReviewInfoList.map { day ->
            ReviewDetailData(
                dayTitle = "${day.dayIndex}일차",
                rating = format1(day.rating),
                pictureList = day.reviewImageUrlList,  // 서버 리스트 그대로
                content = day.content
            )
        }
        reviewDetailAdapter.submit(uiList)
    }

    private fun format1(d: Double): String = String.format("%.1f", d)
}
