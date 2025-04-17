package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSettleBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.SettleDayAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.SettlePayAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.SettlePayerAdapter

class SettleFragment : BaseFragment<FragmentSettleBinding>(R.layout.fragment_settle) {

    private lateinit var payAdapter: SettlePayAdapter

    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
    }

    private fun setClickListener() {

    }

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
    }

    private fun initRecyclerView() {
        val payerList = listOf("기찬", "다희", "동훈", "준하")

        val settlePayMap = mapOf(
            "기찬" to listOf("다희에게 10,000원 주기", "동훈에게 100,000원 주기", "준하에게 70,000원 받기"),
            "다희" to listOf("기찬에게 70,000원 주기", "동훈에게 90,000원 받기", "준하에게 90,000원 받기"),
            "동훈" to listOf("기찬에게 50,000원 받기", "다희에게 100,000원 주기", "준하에게 20,000원 주기"),
            "준하" to listOf("기찬에게 80,000원 받기", "다희에게 30,000원 받기", "동훈에게 70,000원 받기")
        )

        val dailyMap = mapOf(
            "1일차" to listOf("교통, 70,000원", "교통, 60,000원", "입장료, 20,000원"),
            "2일차" to listOf("교통, 70,000원", "식사, 70,000원", "입장료, 40,000원", "입장료, 50,000원"),
            "3일차" to listOf("교통, 50,000원", "식사, 100,000원", "카페, 40,000원", "식사, 40,000원", "교통, 40,000원"),
            "4일차" to listOf("카페, 70,000원", "식사, 30,000원", "교통, 30,000원", "카페, 70,000원", "기념품, 60,000원")
        )

        // 정산자 선택용
        val payerAdapter = SettlePayerAdapter(payerList) { selectedPayer ->
            val payList = settlePayMap[selectedPayer] ?: emptyList()
            payAdapter.updateList(payList)
            binding.rvSettlePay.visibility = View.VISIBLE
        }
        binding.rvSettlePayer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSettlePayer.adapter = payerAdapter

        // 정산 금액 리스트
        payAdapter = SettlePayAdapter(emptyList())
        binding.rvSettlePay.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSettlePay.adapter = payAdapter

        // 날짜별 정산 리스트
        val dayAdapter = SettleDayAdapter(requireContext(), dailyMap)
        binding.rvSettleDay.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSettleDay.adapter = dayAdapter
    }

}