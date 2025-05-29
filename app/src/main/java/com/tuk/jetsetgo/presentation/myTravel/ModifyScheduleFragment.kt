package com.tuk.jetsetgo.presentation.myTravel

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentModifyScheduleBinding
import com.tuk.jetsetgo.domain.model.request.mypage.RecommendAltRequestModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleData
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale


@AndroidEntryPoint
class ModifyScheduleFragment : BaseFragment<FragmentModifyScheduleBinding>(R.layout.fragment_modify_schedule) {
    private val myTravelViewModel: MyTravelViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var isTabSetup = false
    private var initialScheduleList: List<ScheduleData>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initObserver() {
        myTravelViewModel.travelPlanId.observe(viewLifecycleOwner) { id ->
            Log.d("DetailSchedule", "travelPlanId 수신: $id")
            myTravelViewModel.fetchTravelPlan(travelPlanId = id, dayIndex = 1)
        }
        myTravelViewModel.travelPlan.observe(viewLifecycleOwner) { response ->
            val routeInfoList = response.itineraryInfo?.routeInfoList?.map { route ->
                val spot = route.touristSpotInfo
                ScheduleData(
                    routeId = route.routeId,
                    touristSpotId = spot.touristSpotId,
                    title = spot.name,
                    totalTime = myTravelViewModel.getDurationText(route.visitStartTime, route.visitEndTime),
                    startTime = route.visitStartTime,
                    endTime = route.visitEndTime,
                    orderIndex = route.orderIndex,
                    latitude = spot.latitude,
                    longitude = spot.longitude
                )
            } ?: emptyList()

            sharedViewModel.setItineraryId(response.itineraryInfo?.itineraryId)
            sharedViewModel.setRouteInfoList(routeInfoList)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val startDate = LocalDate.parse(response.travelStartDate, formatter)
            val endDate = LocalDate.parse(response.travelEndDate, formatter)

            // x박 x일 텍스트 구성
            val daysBetween = ChronoUnit.DAYS.between(startDate, endDate).toInt()
            val nights = daysBetween
            val days = daysBetween + 1

            val title = "${response.travelName} ${nights}박 ${days}일"
            binding.tvScheduleTitle.text = title

            // 20xx년 x월 ~ x월 생성
            val startYear = startDate.year
            val startMonth = startDate.monthValue
            val endYear = endDate.year
            val endMonth = endDate.monthValue

            val dateText = when {
                startYear == endYear && startMonth == endMonth -> {
                    "${startYear}년 ${startMonth}월"
                }
                startYear == endYear -> {
                    "${startYear}년 ${startMonth}월 ~ ${endMonth}월"
                }
                else -> {
                    "${startYear}년 ${startMonth}월 ~ ${endYear}년 ${endMonth}월"
                }
            }
            binding.tvScheduleDate.text = dateText

            val totalDays = ChronoUnit.DAYS.between(
                LocalDate.parse(response.travelStartDate),
                LocalDate.parse(response.travelEndDate)
            ).toInt() + 1

            if (!isTabSetup) {
                setupTabs(totalDays)
                isTabSetup = true
            }

            // 초기 탭 선택 시 일정 리스트 & 지도 마커 표시
            val dayIndex = myTravelViewModel.currentDayIndex.value ?: 1
            val scheduleList = myTravelViewModel.convertToScheduleData(response)
            scheduleAdapter.submitList(scheduleList)
            initialScheduleList = scheduleList

            sharedViewModel.setItineraryId(response.itineraryInfo?.itineraryId)
            sharedViewModel.setRouteInfoList(scheduleList)

            myTravelViewModel.recommendResult.observe(viewLifecycleOwner) { result ->
                result?.let {
                    Log.d("ModifySchedule", "추천 받은 여행지: $it")
                    // 예시: 다음 화면으로 이동하거나 UI에 출력
                    findNavController().navigate(R.id.modifyToLoading)
                }
            }

        }
    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isTabSetup = false

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setClickListener() {
        binding.ivModifyScheduleBack.setOnClickListener { findNavController().popBackStack() }
        binding.tvModifyClearSelections.setOnClickListener { scheduleAdapter.clearSelections() }
        binding.tvScheduleComplete.setOnClickListener {
            val selectedSchedules = scheduleAdapter.getSelectedItems()
            val modifyIdxList = selectedSchedules.map { it.orderIndex }

            val itineraryList = sharedViewModel.routeInfoList.value?.map { it.touristSpotId }
            val itineraryId = sharedViewModel.itineraryId.value

            if (itineraryList != null && itineraryId != null) {
                val request = RecommendAltRequestModel(
                    itinerary = itineraryList,
                    modifyIdx = modifyIdxList,
                    radius = 99,
                    recommendCount = 5
                )

                myTravelViewModel.postAlternativeRecommend(request)
                // findNavController().navigate(R.id.modifyToLoading)
            } else {
                Toast.makeText(requireContext(), "여행 일정이 비어 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())

        scheduleAdapter = ScheduleAdapter(
            mode = ScheduleAdapter.ScheduleMode.SELECTABLE,
            onScheduleClick = { clickedItem ->
                sharedViewModel.setClickedSchedule(clickedItem)
                // 필요한 경우 추가 처리
            }
        )

        binding.rvSchedule.adapter = scheduleAdapter
    }


    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
    }

    private fun setupTabs(dayCount: Int) {
        val tabLayout = binding.tabLayoutScheduleDate
//        val numberOfDays = scheduleByDay.size
        tabLayout.removeAllTabs()

        for (i in 0 until dayCount) {
            val tab = tabLayout.newTab()
            tab.customView = createTabView(i)
            tabLayout.addTab(tab)
        }

        // 첫 탭을 선택된 스타일로 적용
        updateTabSelectedState(tabLayout.getTabAt(0)!!, true)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val dayIndex = tab.position + 1

                myTravelViewModel.setCurrentDayIndex(dayIndex)

                myTravelViewModel.travelPlanId.value?.let { travelPlanId ->
                    myTravelViewModel.fetchTravelPlan(travelPlanId, dayIndex)
                }

                updateTabSelectedState(tab, true)

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let { updateTabSelectedState(it, false) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createTabView(position: Int): View {
        val view = layoutInflater.inflate(R.layout.item_date, null)
        val tvDayOfWeek = view.findViewById<TextView>(R.id.tv_date_dayOfTheWeek)
        val tvDay = view.findViewById<TextView>(R.id.tv_date_day)

        val startDate = LocalDate.parse(myTravelViewModel.travelPlan.value?.travelStartDate ?: LocalDate.now().toString())
        val targetDate = startDate.plusDays(position.toLong())

        val dayOfWeek = targetDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        val day = targetDate.dayOfMonth

        tvDayOfWeek.text = dayOfWeek
        tvDay.text = "$day"

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
