package com.tuk.jetsetgo.presentation.addTravel

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
import com.naver.maps.map.NaverMap.DEFAULT_CAMERA_POSITION
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelMapBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapAdapter
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelMapFragment : BaseFragment<FragmentTravelMapBinding>(R.layout.fragment_travel_map), OnMapReadyCallback {
    private lateinit var mapAdapter: MapAdapter
    private val viewModel: TravelMapViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var naverMap: NaverMap

    private val lastFragmentId: Int? by lazy {
        arguments?.getInt("lastFragmentId")
    }
    private val selectedPosition: Int? by lazy {
        arguments?.getInt("selectedPosition")
    }

    override fun initObserver() {
        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Log.d("TravelMapFragment", "검색된 장소 리스트: ${response}")
                // 여기에서 검색된 장소 목록을 UI에 반영
            }.onFailure {
                Toast.makeText(requireContext(), "검색 결과를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initView() {
        setupNaverMap()
        initRecyclerView()
        setupSearchButton()
    }

    private fun initRecyclerView() {
        val lastFragmentId = lastFragmentId ?: return
        val selectedPosition = selectedPosition ?: return

        binding.rvTravelMap.layoutManager = LinearLayoutManager(requireContext())
        mapAdapter = MapAdapter(
            onItemClick = { selectedSpot ->
                Log.d("TravelMapFragment", "선택한 장소: ${selectedSpot.name}")

                // 클릭된 장소의 좌표로 카메라 이동
                selectedSpot.latitude?.let { lat ->
                    selectedSpot.longitude?.let { lng ->
                        val location = LatLng(lat, lng)
                        naverMap.moveCamera(CameraUpdate.scrollTo(location).animate(CameraAnimation.Fly))
                    }
                }
            },
            onAddButtonClick = { selectedSpot ->
                Log.d("TravelMapFragment", "추가 버튼 클릭: ${selectedSpot.name}")
                Log.d("TravelMapFragment", "lastFragmentId: ${lastFragmentId}, selectedPosition: ${selectedPosition}")
                when (lastFragmentId) {
                    1 -> { // StartPoint update
                        val updatedNames = sharedViewModel.dailyStartPointName.value.toMutableList()
                        val updatedIds = sharedViewModel.dailyStartPointList.value.toMutableList()

                        updatedNames[selectedPosition] = selectedSpot.name
                        updatedIds[selectedPosition] = selectedSpot.touristSpotId

                        sharedViewModel.setDailyStartPointName(updatedNames)
                        sharedViewModel.setDailyStartPointList(updatedIds)

                        Log.d("TravelMapFragment", "StartPoint update ${selectedPosition+1}일차: ${selectedSpot.name}, ${selectedSpot.touristSpotId}")
                    }
                    2 -> { // Location update
                        // 기존 리스트에 새로운 장소 이름 추가
                        val updatedList = sharedViewModel.travelSpotName.value.toMutableList().apply {
                            add(selectedSpot.name)
                        }
                        sharedViewModel.setTravelSpotName(updatedList)

                        // 기존 리스트에 새로운 장소 ID 추가
                        val updatedIdList = sharedViewModel.travelSpotIdList.value.toMutableList().apply {
                            add(selectedSpot.touristSpotId)
                        }
                        sharedViewModel.setTravelSpotIdList(updatedIdList)
                        Log.d("TravelMapFragment", "Location update: ${sharedViewModel.travelSpotName.value}, ${sharedViewModel.travelSpotIdList.value}")
                    }
                    else -> {
                        Log.w("TravelMapFragment", "알 수 없는 lastFragmentId: $lastFragmentId")
                    }
                }
                findNavController().navigateUp()
            }
        )
        binding.rvTravelMap.adapter = mapAdapter

        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Log.d("TravelMapFragment", "검색된 장소 리스트 업데이트: $response")
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
                        naverMap.moveCamera(CameraUpdate.scrollTo(firstLocation).animate(CameraAnimation.Fly))
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
                            naverMap.moveCamera(CameraUpdate.toCameraPosition(markerPosition).animate(CameraAnimation.Fly))
                            true // 이벤트 소비 (기본 동작 방지)
                        }
                    }
                }
            }.onFailure {
                Log.e("TravelMapFragment", "마커 정보 없음: ${it.message}")
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
