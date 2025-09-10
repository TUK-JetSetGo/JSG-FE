package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import android.view.View
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
import com.tuk.jetsetgo.databinding.FragmentDirectionsBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleData
import com.tuk.jetsetgo.presentation.myTravel.adapter.TravelListAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.getValue


@AndroidEntryPoint
class DirectionsFragment: BaseFragment<FragmentDirectionsBinding>(R.layout.fragment_directions), OnMapReadyCallback {
    private val viewModel: MyTravelViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var travelListAdapter: TravelListAdapter

    private var isTabSetup = false
    private var naverMap: NaverMap? = null
    private val markers = mutableListOf<Marker>()
    private var initialScheduleList: List<ScheduleData>? = null
    private var isInitialCameraMoved = false
    private var currentPath: PathOverlay? = null


    override fun initObserver() {
        viewModel.travelPlanId.observe(viewLifecycleOwner) { id ->
            Log.d("DetailSchedule", "travelPlanId 수신: $id")
            viewModel.fetchTravelPlan(travelPlanId = id, dayIndex = 1)
        }
        viewModel.travelPlan.observe(viewLifecycleOwner) { response ->
            val routeInfoList = response.itineraryInfo?.routeInfoList?.map { route ->
                val spot = route.touristSpotInfo
                ScheduleData(
                    routeId = route.routeId,
                    touristSpotId = spot.touristSpotId,
                    title = spot.name,
                    totalTime = viewModel.getDurationText(route.visitStartTime, route.visitEndTime),
                    startTime = route.visitStartTime,
                    endTime = route.visitEndTime,
                    orderIndex = route.orderIndex,
                    latitude = spot.latitude,
                    longitude = spot.longitude
                )
            } ?: emptyList()

            sharedViewModel.setItineraryId(response.itineraryInfo?.itineraryId)
            sharedViewModel.setRouteInfoList(routeInfoList)

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
            travelListAdapter.submitList(scheduleList)
            initialScheduleList = scheduleList

            sharedViewModel.setItineraryId(response.itineraryInfo?.itineraryId)
            sharedViewModel.setRouteInfoList(scheduleList)

            // 카메라 다시 이동하게 함
            isInitialCameraMoved = false

            // 네이버 맵 준비되었으면 지도 업데이트
            if (naverMap != null) {
                drawMapMarkers(scheduleList)
            }

        }
    }

    override fun initView() {
        initRecyclerView()
        setBackPressedCallback()
        setupNaverMap()
        binding.tvFindDirection.setOnClickListener {
            findNavController().navigate(R.id.goToRoute)
        }
    }

    private fun initRecyclerView() {
        binding.rvTravelMap.layoutManager = LinearLayoutManager(requireContext())
        travelListAdapter = TravelListAdapter(
            onStartClick = { schedule ->
                sharedViewModel.setStartPoint(schedule.latitude, schedule.longitude)
                binding.tvStartPoint.text = schedule.title
            },
            onEndClick = { schedule ->
                sharedViewModel.setEndPoint(schedule.latitude, schedule.longitude)
                binding.tvEndPoint.text = schedule.title
            }
        )
        binding.rvTravelMap.adapter = travelListAdapter
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

                viewModel.setCurrentDayIndex(dayIndex)

                viewModel.travelPlanId.value?.let { travelPlanId ->
                    viewModel.fetchTravelPlan(travelPlanId, dayIndex)
                }

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


        // 터치 시 부모 스크롤 뷰가 가로채지 않도록
        val mapContainer = childFragmentManager.findFragmentById(R.id.map_fragment)?.view
        mapContainer?.setOnTouchListener { _, event ->
            // 부모에게 터치 인터셉트 요청하지 말라고 알림
            mapContainer.parent.requestDisallowInterceptTouchEvent(true)
            // false를 반환해야, 맵 뷰 쪽으로 이벤트가 전달됩니다
            false
        }

        // 처음 들어왔을 때 마커가 먼저 세팅되었고, naverMap 준비 후 이동 안됐던 경우
        if (!isInitialCameraMoved && initialScheduleList != null) {
            drawMapMarkers(initialScheduleList!!)
            isInitialCameraMoved = true
        }

    }

    private fun drawMapMarkers(scheduleList: List<ScheduleData>) {
        // 기존 마커·경로 제거
        markers.forEach { it.map = null }; markers.clear()
        currentPath?.map = null

        val boundsBuilder = LatLngBounds.Builder()

        // 마커 추가
        scheduleList.forEach { item ->
            val lat = item.latitude ?: return@forEach
            val lng = item.longitude ?: return@forEach
            val latLng = LatLng(lat, lng)

            Marker().apply {
                position = LatLng(lat, lng)
                captionText = item.title
                map = naverMap
                markers += this
            }
            boundsBuilder.include(latLng)
        }

        // 첫 장소로 카메라 이동
        if (scheduleList.isNotEmpty()) {
            val bounds = boundsBuilder.build()
            naverMap?.moveCamera(
                CameraUpdate.fitBounds(bounds, 100)
                    .animate(CameraAnimation.Easing)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isTabSetup = false

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }
}