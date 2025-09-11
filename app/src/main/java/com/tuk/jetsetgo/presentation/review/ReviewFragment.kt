package com.tuk.jetsetgo.presentation.review

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentReviewBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.review.adapter.ReviewAdapter
import com.tuk.jetsetgo.presentation.review.adapter.ReviewData
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private val viewModel: ReviewViewModel by viewModels()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun initObserver() {
        setupRecycler()

        // 목록 구독
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reviewListState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            // 필요시 로딩 표시
                        }
                        is UiState.Success -> {
                            // 서버 스펙: overallReviewId, rating, content 만 존재
                            val uiList = state.data.items.map { item ->
                                ReviewData(
                                    title = item.content.take(30), // 미리보기 타이틀 대용
                                    nickname = "익명",             // (스펙 확장 시 교체)
                                    picture = emptyList(),          // 목록엔 이미지 스펙 없음
                                    like = "0",
                                    comment = "0",
                                    bookmark = "0"
                                )
                            }
                            reviewAdapter.submit(uiList)
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), "리뷰 목록 조회 실패", Toast.LENGTH_SHORT).show()
                            Log.e("ReviewFragment", "getReviewList error", state.error)
                        }
                        UiState.Empty -> Unit
                    }
                }
            }
        }

        // 최초 호출
        viewModel.getReviewList(page = 0, size = 20)
    }

    override fun initView() { /* 필요 UI 초기화가 있으면 여기서 */ }

    private fun setupRecycler() {
        // ReviewAdapter는 내부 리스트 보유 + submit(list) 지원(앞서 수정한 버전)
        reviewAdapter = ReviewAdapter { clicked ->
            // ⚠ 목록 API에 travelPlanId가 없어서 상세로 바로 이동 불가
            // 백엔드가 travelPlanId 내려주면 아래 주석 해제
            Toast.makeText(requireContext(), "상세 보기 준비중입니다.", Toast.LENGTH_SHORT).show()

            // 예) travelPlanId가 있다면
            // val bundle = bundleOf("travelPlanId" to travelPlanId)
            // findNavController().navigate(R.id.goToReviewDetail, bundle)
        }

        binding.rvTravelLocation.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
            setHasFixedSize(true)
        }
    }
}
