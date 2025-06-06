package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSpendBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.SpendAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.SpendData
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

@AndroidEntryPoint
class SpendFragment : BaseFragment<FragmentSpendBinding>(R.layout.fragment_spend) {
    private val viewModel: MyTravelViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var spendAdapter: SpendAdapter
    private var isTabSetup = false


    override fun initObserver() {
        viewModel.travelPlan.observe(viewLifecycleOwner) { response ->
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val startDate = LocalDate.parse(response.travelStartDate, formatter)
            val endDate = LocalDate.parse(response.travelEndDate, formatter)

            // x박 x일 텍스트 구성
            val daysBetween = ChronoUnit.DAYS.between(startDate, endDate).toInt()
            val nights = daysBetween
            val days = daysBetween + 1

            val title = "${response.travelName} ${nights}박 ${days}일"
            binding.tvSpendTitle.text = title

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
            binding.tvSpendDate.text = dateText

            val totalDays = ChronoUnit.DAYS.between(
                LocalDate.parse(response.travelStartDate),
                LocalDate.parse(response.travelEndDate)
            ).toInt() + 1

            if (!isTabSetup) {
                setupTabs(totalDays)
                isTabSetup = true
            }
            sharedViewModel.itineraryId.value?.let { itineraryId ->
                viewModel.fetchExpenseDate(itineraryId, page = null, size = null, sort = null)
            }
        }
    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
    }

    private fun setClickListener() {
        binding.ivSpendAddBtn.setOnClickListener { findNavController().navigate(R.id.goToAddSpend) }
    }

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvSpend.layoutManager = LinearLayoutManager(requireContext())

        spendAdapter = SpendAdapter() {
            // 클릭 이벤트 정의
        }

        binding.rvSpend.adapter = spendAdapter
    }

    private fun setupTabs(dayCount: Int) {
        val tabLayout = binding.tabLayoutSpendDate
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
}