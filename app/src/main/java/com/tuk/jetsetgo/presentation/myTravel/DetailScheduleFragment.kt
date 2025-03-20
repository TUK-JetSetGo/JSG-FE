package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentDetailScheduleBinding
import com.tuk.jetsetgo.databinding.FragmentMyTravelBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapAdapter
import com.tuk.jetsetgo.presentation.addTravel.adapter.MapData
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleData
import com.tuk.jetsetgo.presentation.myTravel.adapter.TravelAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.TravelData


class DetailScheduleFragment : BaseFragment<FragmentDetailScheduleBinding>(R.layout.fragment_detail_schedule) {
    private lateinit var scheduleAdapter: ScheduleAdapter

    private val scheduleList = listOf(
        ScheduleData("이동", "30분","AM 09:00","AM 09:30"),
        ScheduleData("N서울타워", "60분","AM 9:30","AM 11:30"),
        ScheduleData("이동", "20분","AM 11:30","AM 11:50"),
        ScheduleData("알돈 을지로점", "70분","AM 11:50","PM 01:00"),
        ScheduleData("이동", "10분","PM 01:00","PM 01:10"),
        ScheduleData("오뷔르 베이커리", "80분","PM 01:10","PM 2:30")
    )
    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setClickListener() {
        
    }

    private fun initRecyclerView() {
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        scheduleAdapter = ScheduleAdapter(scheduleList) {
            // findNavController().navigate(R.id.goToLocation)
        }
        binding.rvSchedule.adapter = scheduleAdapter
    }

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
    }
}