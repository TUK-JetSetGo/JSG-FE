package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelMapBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapAdapter
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapData
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

    private val mapList = listOf(
        MapData("장소1", "장소1 주소 어쩌고 저쩌고", listOf("맛집", "관광지", "주차"), listOf("https://image1.jpg", "https://image2.jpg")),
        MapData("장소2", "장소2 주소 어쩌고 저쩌고", listOf("역사", "문화"), listOf("https://image3.jpg", "https://image4.jpg")),
        MapData("장소3", "장소2 주소 어쩌고 저쩌고", listOf("힐링", "자연", "키워드"), listOf("https://image5.jpg", "https://image6.jpg"))
    )

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
        binding.rvTravelMap.layoutManager = LinearLayoutManager(requireContext())
        mapAdapter = MapAdapter { selectedSpot ->
            Log.d("TravelMapFragment", "선택한 장소: ${selectedSpot.name}")
            findNavController().navigateUp()
        }
        binding.rvTravelMap.adapter = mapAdapter

        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Log.d("TravelMapFragment", "검색된 장소 리스트 업데이트: $response")
                mapAdapter.submitList(response.touristSpotInfoList)
            }.onFailure {
                Toast.makeText(requireContext(), "검색 결과를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
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

    override fun onMapReady(p0: NaverMap) {

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
