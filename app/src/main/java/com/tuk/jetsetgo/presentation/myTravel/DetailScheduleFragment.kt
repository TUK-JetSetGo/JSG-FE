package com.tuk.jetsetgo.presentation.myTravel

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
import com.tuk.jetsetgo.domain.model.request.addTravel.EditPlanRequestModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.RouteInfoModel
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleData
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale


@AndroidEntryPoint
class DetailScheduleFragment : BaseFragment<FragmentDetailScheduleBinding>(R.layout.fragment_detail_schedule), OnMapReadyCallback {
    private val viewModel: MyTravelViewModel by activityViewModels()
    private val osrmViewModel: OsrmViewModel by viewModels()
    private val myTravelViewModel: MyTravelViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
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
            val dayIndex = viewModel.currentDayIndex.value ?: 1
            val scheduleList = viewModel.convertToScheduleData(response)
            scheduleAdapter.submitList(scheduleList)
            initialScheduleList = scheduleList

            sharedViewModel.setItineraryId(response.itineraryInfo?.itineraryId)
            sharedViewModel.setRouteInfoList(scheduleList)

            // 카메라 다시 이동하게 함
            isInitialCameraMoved = false

            // 네이버 맵 준비되었으면 지도 업데이트
            if (naverMap != null) {
                drawMapMarkers(scheduleList)
            }

            if (scheduleList.size >= 2) {
                val coords = scheduleList
                    .map { "${it.longitude},${it.latitude}" }
                    .joinToString(";")
                osrmViewModel.loadRoute(coords)
            }

            osrmViewModel.routeResult.observe(viewLifecycleOwner) { result ->
                result
                    .onSuccess { dto ->
                        // OSRM API 에서 받은 인코딩 문자열 꺼내기
                        val encoded = dto.routes.firstOrNull()?.geometry.orEmpty()
                        if (encoded.isNotEmpty()) {
                            val decoded = decodePolyline(encoded)
                            drawOsrmPath(decoded)
                        }
                    }
                    .onFailure { t ->
                        Toast.makeText(requireContext(), "경로 불러오기 실패: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
        setupNaverMap()
        setupMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isTabSetup = false

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
                    R.id.action_checklist -> {
                        findNavController().navigate(R.id.goToChecklist)
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
        scheduleAdapter = ScheduleAdapter(
            onScheduleClick = {
                // 일정 아이템 전체 클릭 이벤트
            },
            onAddClick = { clickedItem ->
                sharedViewModel.setEditMode(false) // 추가 모드
                sharedViewModel.setClickedSchedule(clickedItem) // 필요 시 추가 저장
                findNavController().navigate(R.id.goToAddSchedule)
            },
            onEditClick = { clickedItem ->
                sharedViewModel.setEditMode(true) // 수정 모드
                sharedViewModel.setClickedSchedule(clickedItem) // 나중에 edit 작업 시 필요
                findNavController().navigate(R.id.goToAddSchedule)
            },
            onDeleteClick = { clickedItem ->
                val itineraryId = sharedViewModel.itineraryId.value ?: return@ScheduleAdapter
                val originalRoutes = sharedViewModel.routeInfoList.value

                // 삭제 대상 제외하고 남은 일정만 추출
                val updatedRoutes = originalRoutes
                    .filter { it.routeId != clickedItem.routeId }
                    .mapIndexed { index, route ->
                        EditPlanRequestModel.RouteModel(
                            routeId = route.routeId,
                            newTouristSpotId = null,
                            visitStartTime = route.startTime,
                            visitEndTime = route.endTime,
                            orderIndex = index + 1 // orderIndex 재정렬
                        )
                    }

                val requestModel = EditPlanRequestModel(routes = updatedRoutes)

                // 요청 확인 로그
                Log.d("ScheduleDelete", "Deleting routeId=${clickedItem.routeId}, request=$requestModel")

                // 삭제 후 여행일정 다시 불러오기
                myTravelViewModel.fetchEditPlan(itineraryId, requestModel, onSuccess = {
                    val travelPlanId = myTravelViewModel.travelPlanId.value
                    val dayIndex = myTravelViewModel.currentDayIndex.value ?: 1
                    if (travelPlanId != null) {
                        myTravelViewModel.fetchTravelPlan(travelPlanId, dayIndex)
                    }
                })
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

        naverMap.uiSettings.isZoomControlEnabled = false

        // 처음 들어왔을 때 마커가 먼저 세팅되었고, naverMap 준비 후 이동 안됐던 경우
        if (!isInitialCameraMoved && initialScheduleList != null) {
            drawMapMarkers(initialScheduleList!!)
            isInitialCameraMoved = true
        }

    }

//    private fun drawMapMarkers(scheduleList: List<ScheduleData>) {
//        if (naverMap == null) return
//
//        markers.forEach { it.map = null }
//        markers.clear()
//
//        val coordinates = scheduleList.mapNotNull {
//            if (it.title != "이동" && it.latitude != null && it.longitude != null)
//                LatLng(it.latitude, it.longitude)
//            else null
//        }
//
//        scheduleList.forEach { item ->
//            if (item.title != "이동" && item.latitude != null && item.longitude != null) {
//                val marker = Marker().apply {
//                    position = LatLng(item.latitude, item.longitude)
//                    captionText = item.title // 마커 이름
//                    map = naverMap
//                }
//                markers.add(marker)
//            }
//        }
//
//        if (coordinates.size >= 2) {
//            // 이전 경로 제거
//            currentPath?.map = null
//
//            // 새로운 경로 생성
//            currentPath = PathOverlay().apply {
//                coords = coordinates
//                color = Color.BLUE
//                width = 10
//                map = naverMap
//            }
//
//            // 카메라 이동 (첫 지점 기준)
//            val bounds = LatLngBounds.Builder().apply {
//                coordinates.forEach { include(it) }
//            }.build()
//
//            val cameraUpdate = CameraUpdate.fitBounds(bounds, 100) // 패딩 100px
//                .animate(CameraAnimation.Easing) // 부드럽게 이동
//
//            naverMap?.moveCamera(cameraUpdate)
//        }
//    }
    private fun drawMapMarkers(scheduleList: List<ScheduleData>) {
        // 기존 마커·경로 제거
        markers.forEach { it.map = null }; markers.clear()
        currentPath?.map = null

        // 마커 추가
        scheduleList.forEach { item ->
            val lat = item.latitude ?: return@forEach
            val lng = item.longitude ?: return@forEach
            Marker().apply {
                position = LatLng(lat, lng)
                captionText = item.title
                map = naverMap
                markers += this
            }
        }
    }

    private fun decodePolyline(encoded: String): List<LatLng> {
        val path = mutableListOf<LatLng>()
        var index = 0; var lat = 0; var lng = 0
        while (index < encoded.length) {
            var result = 0; var shift = 0; var b: Int
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
            lat += dlat

            result = 0; shift = 0
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
            lng += dlng

            path += LatLng(lat / 1e5, lng / 1e5)
        }
        return path
    }

    private fun drawOsrmPath(decoded: List<LatLng>) {
        naverMap ?: return
        currentPath?.map = null
        currentPath = PathOverlay().apply {
            coords = decoded
            width = 10
            color = Color.BLUE
            map = naverMap
        }
        // 카메라 바운딩 (선택)
        val bounds = LatLngBounds.Builder().apply {
            decoded.forEach { include(it) }
        }.build()
        naverMap?.moveCamera(
            CameraUpdate.fitBounds(bounds, 100)
                .animate(CameraAnimation.Easing)
        )
    }

}