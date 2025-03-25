package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMyTravelBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.TravelAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.TravelData


class MyTravelFragment : BaseFragment<FragmentMyTravelBinding>(R.layout.fragment_my_travel) {
    private lateinit var travelAdapter: TravelAdapter

    private val travelList = listOf(
        TravelData("강릉", "2박 3일", "2024.03.17 - 2024.03.19","여행중"),
        TravelData("제주", "5박 6일", "2024.02.24 - 2024.03.01","완료"),
        TravelData("부산", "1박 2일", "2024.01.29 - 2024.01.30","완료"),
        TravelData("서울", "3박 4일", "2024.01.01 - 2024.01.04","완료"),
    )
    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        updateTravelVisibility()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setClickListener() {
        
    }

    private fun initRecyclerView() {
        binding.rvTravelLocation.layoutManager = LinearLayoutManager(requireContext())
        travelAdapter = TravelAdapter(travelList) {
            findNavController().navigate(R.id.goToSchedule)
        }
        binding.rvTravelLocation.adapter = travelAdapter
    }

    private fun updateTravelVisibility() {
        if (travelList.isEmpty()) {
            binding.tvMyTravelNothing.visibility = View.VISIBLE
            binding.rvTravelLocation.visibility = View.GONE
        } else {
            binding.tvMyTravelNothing.visibility = View.GONE
            binding.rvTravelLocation.visibility = View.VISIBLE
        }
    }

}