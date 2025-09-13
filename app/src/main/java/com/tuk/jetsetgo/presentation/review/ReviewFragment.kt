package com.tuk.jetsetgo.presentation.review

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
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

    private var travelPlanIdsByPosition: List<Int> = emptyList()

    override fun initObserver() {
        setupRecycler()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reviewListState.collect { state ->
                    when (state) {
                        is UiState.Loading -> Unit
                        is UiState.Success -> {
                            val items = state.data.items                       // ✅ 도메인은 items 사용
                            travelPlanIdsByPosition = items.map { i -> i.travelPlanId }  // ✅ i로 명시

                            val uiList = items.map { i ->
                                ReviewData(
                                    title    = i.reviewName,   // 예: "제주도 3박 4일"
                                    nickname = "익명",
                                    picture  = emptyList(),    // 목록 API엔 이미지 없음
                                    like     = "0",
                                    comment  = "0",
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

    override fun initView() = Unit

    private fun setupRecycler() {
        reviewAdapter = ReviewAdapter { _, position ->
            val travelPlanId = travelPlanIdsByPosition.getOrNull(position)
            if (travelPlanId == null) {
                Toast.makeText(requireContext(), "선택 항목이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                return@ReviewAdapter
            }
            // ✅ bundle로 상세로 이동
            findNavController().navigate(
                R.id.goToReviewDetail,
                bundleOf("travelPlanId" to travelPlanId)
            )
        }

        binding.rvTravelLocation.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
            setHasFixedSize(true)
        }
    }
}
