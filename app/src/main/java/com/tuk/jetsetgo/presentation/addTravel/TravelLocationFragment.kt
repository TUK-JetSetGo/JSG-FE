package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelLocationBinding
import com.tuk.jetsetgo.domain.model.request.addTravel.CreatePlanRequestModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.LocationAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TravelLocationFragment : BaseFragment<FragmentTravelLocationBinding>(R.layout.fragment_travel_location) {
    private lateinit var locationAdapter: LocationAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addTravelViewModel: AddTravelViewModel by viewModels()

    override fun initObserver() {
    }

    override fun initView() {
        initRecyclerView()
        setupConfirmButton()
        setupAddButton()
    }

    private fun initRecyclerView() {
        binding.rvTravelLocation.layoutManager = LinearLayoutManager(requireContext())
        locationAdapter = LocationAdapter { removedItem ->
            val updatedList = sharedViewModel.travelSpotName.value.toMutableList().apply {
                remove(removedItem)
            }
            sharedViewModel.setTravelSpotName(updatedList)
            toggleConfirmButtonVisibility()
        }
        binding.rvTravelLocation.adapter = locationAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.travelSpotName.collectLatest { locations ->
                Log.d("TravelLocationFragment", "업데이트된 장소 리스트: $locations")
                locationAdapter.submitList(locations)
                toggleConfirmButtonVisibility()
            }
        }
    }

    private fun setupAddButton() {
        binding.clTravelLocationAddBtn.setOnSingleClickListener {
            val action = TravelLocationFragmentDirections.goToMap(lastFragmentId = 2)
            findNavController().navigate(action)
        }
    }

    private fun toggleConfirmButtonVisibility() {
        binding.clTravelLocationConfirmBtn.visibility =
            if (locationAdapter.currentList.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupConfirmButton() {
        binding.clTravelLocationConfirmBtn.setOnSingleClickListener {
            createTravelRequest()
        }
    }

    private fun createTravelRequest() {
        val request = CreatePlanRequestModel(
            isGroup = sharedViewModel.isGroup.value,
            groupSize = sharedViewModel.groupSize.value,
            travelCityId = sharedViewModel.travelCityId.value,
            dailyVisitCount = sharedViewModel.dailyVisitCount.value,
            activityStartTime = sharedViewModel.activityStartTime.value,
            activityEndTime = sharedViewModel.activityEndTime.value,
            travelStartDate = sharedViewModel.travelStartDate.value,
            travelEndDate = sharedViewModel.travelEndDate.value,
            travelPurposeId = sharedViewModel.travelPurposeId.value,
            travelThemeId = sharedViewModel.travelThemeId.value,
            budget = sharedViewModel.budget.value,
            travelSpotIdList = sharedViewModel.travelSpotIdList.value,
            dailyStartPointList = sharedViewModel.dailyStartPointList.value,
            preferredTransport = sharedViewModel.preferredTransport.value
        )

        viewLifecycleOwner.lifecycleScope.launch {
            addTravelViewModel.createTravel(request)
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addTravelViewModel.createTravelState.collectLatest { state ->
                    when (state) {
                        is UiState.Success -> {
                            Log.d("TravelLocationFragment", "일정 생성 성공")
                            Toast.makeText(requireContext(), "일정 생성 성공", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.goToLoading) // 로딩 화면으로 이동
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), "일정 생성 실패: ${state.error?.message}", Toast.LENGTH_SHORT).show()
                            Log.e("TravelLocationFragment", "일정 생성 실패")
                        }
                        else -> {} // 로딩 상태 처리 가능
                    }
                }
            }
        }
    }
}
