package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelPurposeBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.PurposeAdapter
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelPurposeFragment: BaseFragment<FragmentTravelPurposeBinding>(R.layout.fragment_travel_purpose) {
    private val viewModel: TravelCountryViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: PurposeAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun initObserver() {
        viewModel.purposeList.observe(viewLifecycleOwner) { purposeList ->
            adapter.updateList(purposeList)  // Adapter 리스트 갱신
        }
    }

    override fun initView() {
        setupConfirmButton()
        setupBottomSheet()
        setupClickListener()
        setupRecyclerView()
        viewModel.fetchPurpose()
    }

    private fun setupConfirmButton() {
        binding.viewTravelPurposeConfirmBtn.setOnSingleClickListener {
            Log.d("SharedViewModel", "isGroup: ${sharedViewModel.isGroup.value}")
            Log.d("SharedViewModel", "groupSize: ${sharedViewModel.groupSize.value}")
            Log.d("SharedViewModel", "travelCityId: ${sharedViewModel.travelCityId.value}")
            Log.d("SharedViewModel", "dailyVisitCount: ${sharedViewModel.dailyVisitCount.value}")
            Log.d("SharedViewModel", "activityStartTime: ${sharedViewModel.activityStartTime.value}")
            Log.d("SharedViewModel", "activityEndTime: ${sharedViewModel.activityEndTime.value}")
            Log.d("SharedViewModel", "travelPurposeId: ${sharedViewModel.travelPurposeId.value}")
            findNavController().navigate(R.id.goToTheme)
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
                        binding.viewTravelPurposeBackground.visibility = View.VISIBLE
                        binding.viewTravelPurposeBackground.animate().alpha(0.5f).setDuration(200).start()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.viewTravelPurposeBackground.animate()
                            .alpha(0f)
                            .setDuration(200)
                            .withEndAction {
                                binding.viewTravelPurposeBackground.visibility = View.GONE
                            }
                            .start()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드에 따라 배경 알파값 조정 (부드럽게)
                binding.viewTravelPurposeBackground.alpha = slideOffset * 0.5f
            }
        })
    }

    private fun setupClickListener() {
        // TextView 클릭 시 바텀시트 열기
        binding.tvTravelPurposeDropDownPurpose.setOnClickListener {
            toggleBottomSheetState()
        }

        // 배경 터치 시 바텀시트 닫기
        binding.viewTravelPurposeBackground.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setupRecyclerView() {
        adapter = PurposeAdapter { selectedPurpose ->
            sharedViewModel.setTravelPurposeId(selectedPurpose.id) // 선택한 목적 ID 저장
            binding.tvTravelPurposeDropDownPurpose.text = selectedPurpose.name // UI 업데이트
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN // 바텀시트 닫기
        }
        binding.rvPurpose.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPurpose.adapter = adapter
    }

    private fun toggleBottomSheetState() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            binding.viewTravelPurposeBackground.visibility = View.VISIBLE
            binding.viewTravelPurposeBackground.alpha = 0f
            binding.viewTravelPurposeBackground.animate().alpha(0.5f).setDuration(200).start()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}