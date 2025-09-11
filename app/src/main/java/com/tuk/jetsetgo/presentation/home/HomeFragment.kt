package com.tuk.jetsetgo.presentation.home

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentHomeBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.home.adapter.HomePictureAdapter
import com.tuk.jetsetgo.presentation.home.adapter.HomePictureItem
import com.tuk.jetsetgo.presentation.home.adapter.PictureAdapter
import com.tuk.jetsetgo.presentation.myTravel.MyTravelViewModel
import com.tuk.jetsetgo.util.extension.CircleIndicatorDecoration
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: MyTravelViewModel by activityViewModels()

    private lateinit var pictureAdapter: PictureAdapter
    private lateinit var homePictureAdapter: HomePictureAdapter

    //    private val pictureList = mutableListOf("https://image1.jpg", "https://image1.jpg","https://image1.jpg","https://image1.jpg",)
    private val pictureList = mutableListOf(R.drawable.jeju1, R.drawable.jeju2, R.drawable.busan1,R.drawable.bulguksa, R.drawable.mountain1)

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.todayTravel.collect { plan ->
                    if (plan != null) {
                        // 입력/출력 포맷터
                        val inFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val outFmt = DateTimeFormatter.ofPattern("yyyy.MM.dd")

                        val start = LocalDate.parse(plan.travelStartDate, inFmt)
                        val end = LocalDate.parse(plan.travelEndDate, inFmt)
                        val today = LocalDate.now(ZoneId.of("Asia/Seoul"))

                        // 포함구간 기준(+1) 계산
                        val totalDays = ChronoUnit.DAYS.between(start, end).toInt() + 1
                        val currentDay = ChronoUnit.DAYS.between(start, today).toInt() + 1

                        binding.tvHomeOngoingTripBody.text =
                            "${plan.travelName} ${totalDays}일중 ${currentDay}일 째"
                        binding.tvHomeOngoingTripDate.text =
                            "${start.format(outFmt)} ~ ${end.format(outFmt)}"

                        binding.tvHomeOngoingTripBody.isVisible = true
                        binding.tvHomeOngoingTripDate.isVisible = true
                        // binding.groupHomeOngoingTrip?.isVisible = true
                    } else {
                        // 오늘 진행 중인 여행 없음
                        binding.tvHomeOngoingTripBody.text = "오늘 진행 중인 여행이 없어요"
                        binding.tvHomeOngoingTripBody.isVisible = true   // ✅ 보이게!
                        binding.tvHomeOngoingTripDate.isVisible = false  // 날짜는 숨김
                    }
                }
            }
        }
    }

    override fun initView() {
        bottomNavigationVisible()
        initRecyclerView()
        initHomePictureRecyclerView()
        goToPlan()
    }

    private fun bottomNavigationVisible() {
        // BottomNavigationView 보이기
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.rvHomeLastTripPicture.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        pictureAdapter = PictureAdapter(pictureList)
        binding.rvHomeLastTripPicture.adapter = pictureAdapter
    }

    private fun initHomePictureRecyclerView() {
        val dummyList = listOf(
            HomePictureItem(R.drawable.jeju1, "노란 유채꽃이 만개한 \n따뜻한 봄길"),
            HomePictureItem(R.drawable.busan1, "찬란하게 빛나는 \n부산의 밤 바다"),
            HomePictureItem(R.drawable.bulguksa, "고요함이 감도는 \n불국사의 아침"),
            HomePictureItem(R.drawable.jeju2, "제주의 햇살 담은 \n상큼한 감귤 향기"),
            HomePictureItem(R.drawable.mountain1, "지리산 능선을 따라 걷는 \n고요한 산책")
        )

        homePictureAdapter = HomePictureAdapter(dummyList)
        binding.rvHomePicture.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homePictureAdapter

            // Snap Helper 추가
            LinearSnapHelper().attachToRecyclerView(this)
            // Circle Indicator 추가
            addItemDecoration(CircleIndicatorDecoration())
        }
    }

    private fun goToPlan() {
        binding.tvHomeWhereToGO.setOnClickListener {
            findNavController().navigate(R.id.addTravelTab)
        }
    }
}