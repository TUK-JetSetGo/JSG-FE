package com.tuk.jetsetgo.presentation.myTravel

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentDetailScheduleBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleData
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale


@AndroidEntryPoint
class DetailScheduleFragment : BaseFragment<FragmentDetailScheduleBinding>(R.layout.fragment_detail_schedule), OnMapReadyCallback {
    private val viewModel: MyTravelViewModel by activityViewModels()
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var currentPath: PathOverlay? = null
    private var naverMap: NaverMap? = null
    private val markers = mutableListOf<Marker>()
    private var isTabSetup = false

    private var initialScheduleList: List<ScheduleData>? = null
    private var isInitialCameraMoved = false

    override fun initObserver() {
        viewModel.travelPlanId.observe(viewLifecycleOwner) { id ->
            Log.d("DetailSchedule", "travelPlanId 수신: $id")
            viewModel.fetchTravelPlan(travelPlanId = id, dayIndex = 1)
        }
        viewModel.travelPlan.observe(viewLifecycleOwner) { response ->
            val totalDays = ChronoUnit.DAYS.between(
                LocalDate.parse(response.travelStartDate),
                LocalDate.parse(response.travelEndDate)
            ).toInt() + 1

            if (!isTabSetup) {
                setupTabs(totalDays)
                isTabSetup = true
            }

            // 초기 탭 선택 시 일정 리스트 & 지도 마커 표시
            val dayIndex = viewModel.currentDayIndex.value ?: 1
            val scheduleList = viewModel.convertToScheduleData(response)
            scheduleAdapter.submitList(scheduleList)
            initialScheduleList = scheduleList
            drawMapMarkers(scheduleList)
        }
    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
//        setupTabs()
        setupNaverMap()
        setupMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setClickListener() {

    }

    private fun setupMenu(){
        binding.viewHamburgerMenu.setOnClickListener { anchor ->
            val popup = PopupMenu(requireContext(), anchor, 0, 0, R.style.WhitePopupMenu)
            popup.menuInflater.inflate(R.menu.menu_detail_schedule, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_spend -> {
                        findNavController().navigate(R.id.goToSpend)
                        true
                    }
                    R.id.action_settle -> {
                        findNavController().navigate(R.id.goToSettle)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

    }
    private fun initRecyclerView() {
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())

        // 초기값은 Day 1 (position = 0)
//        val initialSchedule = scheduleByDay[0] ?: emptyList()

        scheduleAdapter = ScheduleAdapter {
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

    private fun setupTabs(dayCount: Int) {
        val tabLayout = binding.tabLayoutScheduleDate
//        val numberOfDays = scheduleByDay.size
        tabLayout.removeAllTabs()

        for (i in 0 until dayCount) {
            val tab = tabLayout.newTab()
            tab.customView = createTabView(i)
            tabLayout.addTab(tab)
        }

//        // 기본 첫 탭 선택 시 리스트 + 스타일 모두 초기화
//        val firstSchedule = scheduleByDay[0] ?: emptyList()
//        scheduleAdapter.updateList(firstSchedule)

        // 첫 탭을 선택된 스타일로 적용
        updateTabSelectedState(tabLayout.getTabAt(0)!!, true)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val dayIndex = tab.position + 1

                viewModel.setCurrentDayIndex(dayIndex)

                viewModel.travelPlanId.value?.let { travelPlanId ->
                    viewModel.fetchTravelPlan(travelPlanId, dayIndex)
                }


//                val selectedList = scheduleByDay[position] ?: emptyList()
//                scheduleAdapter.updateList(selectedList)
//
//                drawPathForDay(position)

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

        val startDate = LocalDate.parse(viewModel.travelPlan.value?.travelStartDate ?: LocalDate.now().toString())
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

    private fun setupNaverMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.uiSettings.isZoomControlEnabled = false

        // 처음 들어왔을 때 마커가 먼저 세팅되었고, naverMap 준비 후 이동 안됐던 경우
        if (!isInitialCameraMoved && initialScheduleList != null) {
            drawMapMarkers(initialScheduleList!!)
            isInitialCameraMoved = true
        }

    }

    private fun drawMapMarkers(scheduleList: List<ScheduleData>) {
        if (naverMap == null) return

        markers.forEach { it.map = null }
        markers.clear()

        val coordinates = scheduleList.mapNotNull {
            if (it.title != "이동" && it.latitude != null && it.longitude != null)
                LatLng(it.latitude, it.longitude)
            else null
        }

        scheduleList.forEach { item ->
            if (item.title != "이동" && item.latitude != null && item.longitude != null) {
                val marker = Marker().apply {
                    position = LatLng(item.latitude, item.longitude)
                    captionText = item.title // 마커 이름
                    map = naverMap
                }
                markers.add(marker)
            }
        }

        if (coordinates.size >= 2) {
            // 이전 경로 제거
            currentPath?.map = null

            // 새로운 경로 생성
            currentPath = PathOverlay().apply {
                coords = coordinates
                color = Color.BLUE
                width = 10
                map = naverMap
            }

            // 카메라 이동 (첫 지점 기준)
            val bounds = LatLngBounds.Builder().apply {
                coordinates.forEach { include(it) }
            }.build()

            val cameraUpdate = CameraUpdate.fitBounds(bounds, 100) // 패딩 100px
                .animate(CameraAnimation.Easing) // 부드럽게 이동

            naverMap?.moveCamera(cameraUpdate)
        }
    }

}