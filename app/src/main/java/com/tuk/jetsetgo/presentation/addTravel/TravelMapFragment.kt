package com.tuk.jetsetgo.presentation.addTravel

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelMapBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapAdapter
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapData
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelMapFragment : BaseFragment<FragmentTravelMapBinding>(R.layout.fragment_travel_map) {
    private lateinit var mapAdapter: MapAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addTravelViewModel: AddTravelViewModel by viewModels()

    private val mapList = listOf(
        MapData("장소1", "장소1 주소 어쩌고 저쩌고", listOf("맛집", "관광지", "주차"), listOf("https://image1.jpg", "https://image2.jpg")),
        MapData("장소2", "장소2 주소 어쩌고 저쩌고", listOf("역사", "문화"), listOf("https://image3.jpg", "https://image4.jpg")),
        MapData("장소3", "장소2 주소 어쩌고 저쩌고", listOf("힐링", "자연", "키워드"), listOf("https://image5.jpg", "https://image6.jpg"))
    )

    override fun initObserver() {
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvTravelMap.layoutManager = LinearLayoutManager(requireContext())
        mapAdapter = MapAdapter(mapList) {
            findNavController().navigate(R.id.goToLocation)
        }
        binding.rvTravelMap.adapter = mapAdapter
    }

}
