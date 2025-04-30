package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelMapBinding
import com.tuk.jetsetgo.domain.model.request.addTravel.EditPlanRequestModel
import com.tuk.jetsetgo.presentation.addTravel.TravelMapViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapAdapter
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class AddScheduleFragment : BaseFragment<FragmentTravelMapBinding>(R.layout.fragment_travel_map), OnMapReadyCallback {
    private lateinit var mapAdapter: MapAdapter
    private val viewModel: TravelMapViewModel by activityViewModels()
    private val myTravelViewModel: MyTravelViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var naverMap: NaverMap

    override fun initObserver() {
        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Log.d("AddScheduleFragment", "검색된 장소 리스트: ${response}")
                // 여기에서 검색된 장소 목록을 UI에 반영
            }.onFailure {
                Toast.makeText(requireContext(), "검색 결과를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initView() {
        initRecyclerView()
        setupNaverMap()
        setupSearchButton()
    }

    private fun initRecyclerView() {
        binding.rvTravelMap.layoutManager = LinearLayoutManager(requireContext())
        mapAdapter = MapAdapter(
            onItemClick = { selectedSpot ->
                Log.d("AddScheduleFragment", "선택한 장소: ${selectedSpot.name}")

                // 클릭된 장소의 좌표로 카메라 이동
                selectedSpot.latitude?.let { lat ->
                    selectedSpot.longitude?.let { lng ->
                        val location = LatLng(lat, lng)
                        naverMap.moveCamera(CameraUpdate.scrollTo(location).animate(CameraAnimation.Fly))
                    }
                }
            },
            onAddButtonClick = { selectedSpot ->
                val itineraryId = sharedViewModel.itineraryId.value ?: return@MapAdapter
                val prevRoute = sharedViewModel.clickedSchedule.value ?: return@MapAdapter
                val now = LocalDateTime.now().toString()
                val isEditMode = sharedViewModel.isEditMode.value
                val originalRoutes = sharedViewModel.routeInfoList.value

                val updatedRoutes = mutableListOf<EditPlanRequestModel.RouteModel>()

                if (isEditMode) {
                    // 일정 수정: 기존 route 제거하고 같은 위치에 새로운 장소로 교체
                    originalRoutes.forEach {
                        if (it.routeId != prevRoute.routeId) {
                            updatedRoutes.add(
                                EditPlanRequestModel.RouteModel(
                                    routeId = it.routeId,
                                    newTouristSpotId = null,
                                    visitStartTime = it.startTime,
                                    visitEndTime = it.endTime,
                                    orderIndex = it.orderIndex
                                )
                            )
                        }
                    }

                    // 수정용 route (같은 위치에 삽입하되 routeId 없이)
                    updatedRoutes.add(
                        EditPlanRequestModel.RouteModel(
                            routeId = null,
                            newTouristSpotId = selectedSpot.touristSpotId,
                            visitStartTime = prevRoute.startTime,
                            visitEndTime = prevRoute.endTime,
                            orderIndex = prevRoute.orderIndex
                        )
                    )
                } else {
                    // 일정 추가: prevRoute 다음에 새 route 추가 + 이후 일정 orderIndex 밀기
                    var inserted = false
                    originalRoutes.forEach {
                        val originalIndex = it.orderIndex
                        if (!inserted && originalIndex == prevRoute.orderIndex) {
                            // 기존 route 먼저
                            updatedRoutes.add(
                                EditPlanRequestModel.RouteModel(
                                    routeId = it.routeId,
                                    newTouristSpotId = null,
                                    visitStartTime = it.startTime,
                                    visitEndTime = it.endTime,
                                    orderIndex = originalIndex
                                )
                            )
                            // 새 일정 삽입
                            updatedRoutes.add(
                                EditPlanRequestModel.RouteModel(
                                    routeId = null,
                                    newTouristSpotId = selectedSpot.touristSpotId,
                                    visitStartTime = it.startTime,
                                    visitEndTime = it.endTime,
                                    orderIndex = originalIndex + 1
                                )
                            )
                            inserted = true
                        } else {
                            updatedRoutes.add(
                                EditPlanRequestModel.RouteModel(
                                    routeId = it.routeId,
                                    newTouristSpotId = null,
                                    visitStartTime = it.startTime,
                                    visitEndTime = it.endTime,
                                    orderIndex = if (inserted) originalIndex + 1 else originalIndex
                                )
                            )
                        }
                    }
                }

                // 최종 orderIndex 재정렬 (1부터 시작)
                val finalRoutes = updatedRoutes
                    .sortedBy { it.orderIndex }
                    .mapIndexed { idx, it -> it.copy(orderIndex = idx + 1) }

                val requestModel = EditPlanRequestModel(routes = finalRoutes)
                myTravelViewModel.fetchEditPlan(itineraryId, requestModel)

                findNavController().navigateUp()
            }

        )
        binding.rvTravelMap.adapter = mapAdapter

        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Log.d("AddScheduleFragment", "검색된 장소 리스트 업데이트: $response")
                val places = response.touristSpotInfoList
                mapAdapter.submitList(places)

                // 장소가 없으면 "장소 정보 없음" 표시, 있으면 RecyclerView 표시
                if (places.isEmpty()) {
                    binding.rvTravelMap.visibility = View.GONE
                    binding.layoutNoPlace.visibility = View.VISIBLE
                } else {
                    binding.rvTravelMap.visibility = View.VISIBLE
                    binding.layoutNoPlace.visibility = View.GONE
                }
            }.onFailure {
                Toast.makeText(requireContext(), "검색 결과를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                binding.rvTravelMap.visibility = View.GONE
                binding.layoutNoPlace.visibility = View.VISIBLE
            }
        }
    }

    private fun setupNaverMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                val spotList = response.touristSpotInfoList

                if (spotList.isNotEmpty()) {
                    // 첫 번째 마커의 위치 가져오기
                    val firstSpot = spotList.first()
                    if (firstSpot.latitude != null && firstSpot.longitude != null) {
                        val firstLocation = LatLng(firstSpot.latitude, firstSpot.longitude)

                        // 첫 번째 마커 위치로 초기 카메라 이동
                        naverMap.moveCamera(
                            CameraUpdate.scrollTo(firstLocation).animate(
                                CameraAnimation.Fly))
                    }
                }

                // 마커 추가
                spotList.forEach { location ->
                    if (location.latitude != null && location.longitude != null) {
                        val marker = Marker().apply {
                            position = LatLng(location.latitude, location.longitude)
                            map = naverMap
                        }

                        // 마커 클릭 시 해당 위치로 카메라 이동
                        marker.setOnClickListener {
                            val markerPosition = CameraPosition(marker.position, 16.0)
                            naverMap.moveCamera(
                                CameraUpdate.toCameraPosition(markerPosition).animate(
                                    CameraAnimation.Fly))
                            true // 이벤트 소비 (기본 동작 방지)
                        }
                    }
                }
            }.onFailure {
                Log.e("AddScheduleFragment", "마커 정보 없음: ${it.message}")
            }
        }
    }

    private fun setupSearchButton() {
        binding.tvTravelMapSearch.setOnSingleClickListener {
            findNavController().navigate(R.id.goToSearch)
        }
        binding.ivTravelMapArrow.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}