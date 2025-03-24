package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
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

    private val scheduleByDay = mapOf(
        0 to listOf(
            ScheduleData("이동", "30분","AM 09:00","AM 09:30"),
            ScheduleData("N서울타워", "60분","AM 9:30","AM 11:30"),
            ScheduleData("이동", "30분","AM 09:00","AM 09:30"),
            ScheduleData("N서울타워", "60분","AM 9:30","AM 11:30"),
            ScheduleData("이동", "30분","AM 09:00","AM 09:30"),
            ScheduleData("N서울타워", "60분","AM 9:30","AM 11:30"),
        ),
        1 to listOf(
            ScheduleData("이동", "20분","AM 10:00","AM 10:20"),
            ScheduleData("카페", "90분","AM 10:20","AM 11:50")
        ),
        2 to listOf(
            ScheduleData("쇼핑", "120분","PM 01:00","PM 03:00")
        ),
        3 to listOf(
            ScheduleData("쇼핑", "120분","PM 01:00","PM 03:00")
        ),
        4 to listOf(
            ScheduleData("쇼핑", "120분","PM 01:00","PM 03:00")
        )
    )

    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
        setupTabs()
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

        // 초기값은 Day 1 (position = 0)
        val initialSchedule = scheduleByDay[0] ?: emptyList()

        scheduleAdapter = ScheduleAdapter(initialSchedule) {
            // 클릭 이벤트 정의
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

    private fun setupTabs() {
        val tabLayout = binding.tabLayoutScheduleDate
        val numberOfDays = scheduleByDay.size

        for (i in 0 until numberOfDays) {
            val tab = tabLayout.newTab()
            tab.customView = createTabView(i)
            tabLayout.addTab(tab)
        }

        // 기본 첫 탭 선택 시 리스트 + 스타일 모두 초기화
        val firstSchedule = scheduleByDay[0] ?: emptyList()
        scheduleAdapter.updateList(firstSchedule)

        // 첫 탭을 선택된 스타일로 적용
        updateTabSelectedState(tabLayout.getTabAt(0)!!, true)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                val selectedList = scheduleByDay[position] ?: emptyList()
                scheduleAdapter.updateList(selectedList)

                updateTabSelectedState(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let { updateTabSelectedState(it, false) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun createTabView(position: Int): View {
        val view = layoutInflater.inflate(R.layout.item_date, null)
        val tvDayOfWeek = view.findViewById<TextView>(R.id.tv_date_dayOfTheWeek)
        val tvDay = view.findViewById<TextView>(R.id.tv_date_day)

        // 예시: Day 1 → "월 / 1"
        val dayOfWeekList = listOf("화", "수", "목", "금", "토", "일", "월")
        val dayIndex = position % 7
        tvDayOfWeek.text = dayOfWeekList[dayIndex]
        tvDay.text = "${position + 1}"

        return view
    }

    private fun updateTabSelectedState(tab: TabLayout.Tab, isSelected: Boolean) {
        val tabView = tab.customView ?: return
        val clDate = tabView.findViewById<ConstraintLayout>(R.id.cl_date)
        val tvDayOfWeek = tabView.findViewById<TextView>(R.id.tv_date_dayOfTheWeek)
        val tvDay = tabView.findViewById<TextView>(R.id.tv_date_day)

        if (isSelected) {
            clDate.setBackgroundResource(R.drawable.shape_rect_999_black_fill) // 선택 배경
            tvDayOfWeek.setTextColor(resources.getColor(R.color.white, null))
            tvDay.setTextColor(resources.getColor(R.color.white, null))
        } else {
            clDate.setBackgroundResource(R.drawable.shape_rect_999_white_fill) // 비선택 배경
            tvDayOfWeek.setTextColor(resources.getColor(R.color.black, null))
            tvDay.setTextColor(resources.getColor(R.color.black, null))
        }
    }


}