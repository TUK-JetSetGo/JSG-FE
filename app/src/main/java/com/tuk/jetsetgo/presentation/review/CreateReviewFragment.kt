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
import com.tuk.jetsetgo.databinding.FragmentCreateReviewBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.review.adapter.CreateReviewAdapter
import com.tuk.jetsetgo.presentation.review.adapter.CreateReviewItemState
import com.tuk.jetsetgo.domain.model.request.review.DailyReviewItemModel
import com.tuk.jetsetgo.domain.model.request.review.PostReviewRequestModel
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateReviewFragment :
    BaseFragment<FragmentCreateReviewBinding>(R.layout.fragment_create_review) {

    private val viewModel: ReviewViewModel by viewModels()

    private val args: CreateReviewFragmentArgs by navArgs()

    private lateinit var adapter: CreateReviewAdapter
    private var totalRating: Float = 0f

    override fun initObserver() {
        // 뒤로가기
        binding.ivCreateReviewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // postReview 상태
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postReviewState.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            Log.d("CreateReviewFragment", "리뷰 저장 중...")
                        }

                        is UiState.Success -> {
                            Log.d("CreateReviewFragment", "리뷰 저장 성공")
                            Toast.makeText(requireContext(), "리뷰가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }

                        is UiState.Error -> {
                            Toast.makeText(requireContext(), "리뷰 저장 실패", Toast.LENGTH_SHORT).show()
                            Log.e("CreateReviewFragment", "리뷰 저장 실패: ${state.error?.message}")
                        }

                        UiState.Empty -> {
                            Log.d("CreateReviewFragment", "postReviewState: Empty")
                        }
                    }
                }
            }
        }

    }

    override fun initView() = with(binding) {
        // 1) 총점 RatingBar 리스너
        createReviewRatingBarTotal.setOnRatingBarChangeListener { _, rating, _ ->
            totalRating = rating
        }

        // 2) RV 세팅 (xml id: rv_createReview_day_rating)
        rvCreateReviewDayRating.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val initialItems = (1..3).map { day -> CreateReviewItemState(day = day) }.toMutableList()
        adapter = CreateReviewAdapter(initialItems)
        rvCreateReviewDayRating.adapter = adapter

        // 3) 완료 버튼: 입력값 → Domain 모델 → ViewModel.postReview
        tvCreateReviewComplete.setOnClickListener {
            val overallContent = etCreateReviewTotalReview.text?.toString()?.trim().orEmpty()
            if (overallContent.isBlank()) {
                Toast.makeText(requireContext(), "전체 일정 후기를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (totalRating <= 0f) {
                Toast.makeText(requireContext(), "전체 별점을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val perDayStates = adapter.getStates()
            val dailyList = perDayStates.map {
                DailyReviewItemModel(
                    dayIndex = it.day,
                    rating = it.rating.toDouble(),
                    content = it.comment // 비워둘 수도 있으니 서버 요구사항에 맞춰 검증 필요
                )
            }

            val body = PostReviewRequestModel(
                overallRating = totalRating.toDouble(),
                overallContent = overallContent,
                dailyList = dailyList
            )

            viewModel.postReview(
                travelPlanId = args.travelPlanId,
                body = body
            )
        }
    }
}
