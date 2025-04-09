package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSpendBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.SpendAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.SpendData

class SpendFragment : BaseFragment<FragmentSpendBinding>(R.layout.fragment_spend) {

    private lateinit var spendAdapter: SpendAdapter

    private val spendByDay = mapOf(
        0 to listOf(
            SpendData("식당", "100,000", "다희","기찬, 다희, 준하, 동훈"),
            SpendData("카페", "70,000", "기찬","준하, 동훈"),
            SpendData("쇼핑", "200,000", "준하","기찬, 다희"),
            SpendData("편의점", "40,000", "동훈","기찬, 다희, 준하, 동훈"),
        ),
        1 to listOf(
            SpendData("식당", "100,000", "다희","기찬, 다희, 준하, 동훈"),
            SpendData("카페", "70,000", "기찬","준하, 동훈"),
        ),
        2 to listOf(
            SpendData("쇼핑", "200,000", "준하","기찬, 다희"),
            SpendData("편의점", "40,000", "동훈","기찬, 다희, 준하, 동훈"),
        ),
        3 to listOf(
            SpendData("식당", "100,000", "다희","기찬, 다희, 준하, 동훈"),
            SpendData("카페", "70,000", "기찬","준하, 동훈"),
            SpendData("쇼핑", "200,000", "준하","기찬, 다희"),
            SpendData("편의점", "40,000", "동훈","기찬, 다희, 준하, 동훈"),
        ),
        4 to listOf(
            SpendData("식당", "100,000", "다희","기찬, 다희, 준하, 동훈"),
            SpendData("쇼핑", "200,000", "준하","기찬, 다희"),
            SpendData("편의점", "40,000", "동훈","기찬, 다희, 준하, 동훈"),
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

        // 초기값은 Day 1 (position = 0)
        val initialSpend = spendByDay[0] ?: emptyList()

        spendAdapter = SpendAdapter(initialSpend) {
            // 클릭 이벤트 정의
        }

        binding.rvSpend.adapter = spendAdapter
    }

    private fun setupTabs() {
        val tabLayout = binding.tabLayoutSpendDate
        val numberOfDays = spendByDay.size

        for (i in 0 until numberOfDays) {
            val tab = tabLayout.newTab()
            tab.customView = createTabView(i)
            tabLayout.addTab(tab)
        }

        // 기본 첫 탭 선택 시 리스트 + 스타일 모두 초기화
        val firstDay = spendByDay[0] ?: emptyList()
        spendAdapter.updateList(firstDay)

        // 첫 탭을 선택된 스타일로 적용
        updateTabSelectedState(tabLayout.getTabAt(0)!!, true)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                val selectedList = spendByDay[position] ?: emptyList()
                spendAdapter.updateList(selectedList)

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