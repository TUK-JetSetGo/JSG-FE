package com.tuk.jetsetgo.presentation.myTravel

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
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


@AndroidEntryPoint
class DetailScheduleFragment : BaseFragment<FragmentDetailScheduleBinding>(R.layout.fragment_detail_schedule), OnMapReadyCallback {
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var currentPath: PathOverlay? = null
    private var naverMap: NaverMap? = null
    private val markers = mutableListOf<Marker>()


    private val scheduleByDay = mapOf(
        0 to listOf(
            ScheduleData("돌하르방미술관", "60분","AM 9:30","AM 11:30",33.5388536,126.6888142),
            ScheduleData("이동", "30분","AM 09:00","AM 09:30", null,null),
            ScheduleData("함덕해수욕장", "60분","AM 9:30","AM 11:30",33.5431082,126.6696924),
            ScheduleData("이동", "30분","AM 09:00","AM 09:30", null,null),
            ScheduleData("금능석물원", "60분","AM 9:30","AM 11:30",33.3848796,126.2272967),
        ),
        1 to listOf(
            ScheduleData("항파두리항몽유적지", "90분","AM 10:20","AM 11:50",33.4527482,126.407778),
            ScheduleData("이동", "20분","AM 10:00","AM 10:20", null,null),
            ScheduleData("비자림", "90분","AM 10:20","AM 11:50",33.4843445,126.8066615)
        ),
        2 to listOf(
            ScheduleData("제주김녕미로공원", "120분","PM 01:00","PM 03:00",33.5362944,126.7720257),
            ScheduleData("이동", "20분","AM 10:00","AM 10:20", null,null),
            ScheduleData("비자림", "90분","AM 10:20","AM 11:50",33.4843445,126.8066615),
            ScheduleData("이동", "20분","AM 10:00","AM 10:20", null,null),
            ScheduleData("항파두리항몽유적지", "90분","AM 10:20","AM 11:50",33.4527482,126.407778),
        ),
        3 to listOf(
            ScheduleData("렛츠런파크 제주", "120분","PM 01:00","PM 03:00",33.4098459,126.3931367),
            ScheduleData("이동", "20분","AM 10:00","AM 10:20", null,null),
            ScheduleData("금능석물원", "60분","AM 9:30","AM 11:30",33.3848796,126.2272967),
            ScheduleData("이동", "20분","AM 10:00","AM 10:20", null,null),
            ScheduleData("제주김녕미로공원", "120분","PM 01:00","PM 03:00",33.5362944,126.7720257)
        ),
        4 to listOf(
            ScheduleData("비자림", "90분","AM 10:20","AM 11:50",33.4843445,126.8066615),
            ScheduleData("이동", "20분","AM 10:00","AM 10:20", null,null),
            ScheduleData("렛츠런파크 제주", "120분","PM 01:00","PM 03:00",33.4098459,126.3931367),
            ScheduleData("이동", "20분","AM 10:00","AM 10:20", null,null),
            ScheduleData("금능석물원", "60분","AM 9:30","AM 11:30",33.3848796,126.2272967)
        )
    )

    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
        setupTabs()
        setupNaverMap()
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

                drawPathForDay(position)

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

    private fun setupNaverMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        drawPathForDay(0) // 초기 경로 표시
        naverMap.uiSettings.isZoomControlEnabled = false

    }

    private fun drawPathForDay(dayIndex: Int) {
        val scheduleList = scheduleByDay[dayIndex] ?: return

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