package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelThemeBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.ThemeAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelThemeFragment:BaseFragment<FragmentTravelThemeBinding>(R.layout.fragment_travel_theme) {
    private val viewModel: TravelCountryViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: ThemeAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    override fun initObserver() {
        viewModel.themeList.observe(viewLifecycleOwner) { themeList ->
            adapter.updateList(themeList)  // Adapter 리스트 갱신
        }
    }

    override fun initView() {
        setupConfirmButton()
        setupBottomSheet()
        setupClickListener()
        setupRecyclerView()
        viewModel.fetchThemes()
    }

    private fun setupConfirmButton() {
        binding.viewTravelThemeConfirmBtn.setOnSingleClickListener {
            Log.d("SharedViewModel", "isGroup: ${sharedViewModel.isGroup.value}")
            Log.d("SharedViewModel", "groupSize: ${sharedViewModel.groupSize.value}")
            Log.d("SharedViewModel", "travelCityId: ${sharedViewModel.travelCityId.value}")
            Log.d("SharedViewModel", "dailyVisitCount: ${sharedViewModel.dailyVisitCount.value}")
            Log.d("SharedViewModel", "activityStartTime: ${sharedViewModel.activityStartTime.value}")
            Log.d("SharedViewModel", "activityEndTime: ${sharedViewModel.activityEndTime.value}")
            Log.d("SharedViewModel", "travelPurposeId: ${sharedViewModel.travelPurposeId.value}")
            Log.d("SharedViewModel", "travelThemeId: ${sharedViewModel.travelThemeId.value}")
            findNavController().navigate(R.id.goToBudget)
        }
    }

    private fun setupBottomSheet() {
        // 바텀시트 초기화
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layoutBottomSheetContainer)

        // 기본 상태를 숨김(HIDDEN)으로 설정
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.isHideable = true // 숨김 가능하도록 설정
        bottomSheetBehavior.skipCollapsed = true // COLLAPSED 상태 건너뛰기
        bottomSheetBehavior.peekHeight = 0 // 기본 높이 0 설정

        // 바텀시트 외부를 터치하면 바텀시트 닫기
        binding.root.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.viewTravelThemeBackground.visibility = View.VISIBLE
                        binding.viewTravelThemeBackground.animate().alpha(0.5f).setDuration(200).start()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.viewTravelThemeBackground.animate()
                            .alpha(0f)
                            .setDuration(200)
                            .withEndAction {
                                binding.viewTravelThemeBackground.visibility = View.GONE
                            }
                            .start()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드에 따라 배경 알파값 조정 (부드럽게)
                binding.viewTravelThemeBackground.alpha = slideOffset * 0.5f
            }
        })
    }

    private fun setupClickListener() {
        binding.ivAddTravelBack.setOnClickListener { findNavController().popBackStack() }

        // TextView 클릭 시 바텀시트 열기
        binding.tvTravelThemeDropDownTheme.setOnSingleClickListener {
            toggleBottomSheetState()
        }

        // 배경 터치 시 바텀시트 닫기
        binding.viewTravelThemeBackground.setOnSingleClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setupRecyclerView() {
        adapter = ThemeAdapter { selectedTheme ->
            sharedViewModel.setTravelThemeId(selectedTheme.id) // 선택한 목적 ID 저장
            binding.tvTravelThemeDropDownTheme.text = selectedTheme.name // UI 업데이트
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN // 바텀시트 닫기
        }
        binding.rvTheme.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTheme.adapter = adapter
    }

    private fun toggleBottomSheetState() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            binding.viewTravelThemeBackground.visibility = View.VISIBLE
            binding.viewTravelThemeBackground.alpha = 0f
            binding.viewTravelThemeBackground.animate().alpha(0.5f).setDuration(200).start()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}