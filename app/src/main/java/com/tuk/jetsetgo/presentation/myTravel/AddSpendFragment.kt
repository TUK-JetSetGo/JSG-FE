package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentAddSpendBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.AddSpendAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.AddSpendIndividualAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSpendFragment : BaseFragment<FragmentAddSpendBinding>(R.layout.fragment_add_spend) {

    private lateinit var addSpendAdapter: AddSpendAdapter


    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        setBackPressedCallback()
        setPaymentMethod(isSplit = true)
    }

    private fun setClickListener() {
        binding.ivAddSpendBack.setOnClickListener { findNavController().popBackStack() }
        binding.tvAddSpendComplete.setOnClickListener { findNavController().popBackStack() }

        binding.tvAddSpendSplit.setOnClickListener {
            setPaymentMethod(isSplit = true)
        }

        binding.tvAddSpendIndividual.setOnClickListener {
            setPaymentMethod(isSplit = false)
        }
    }

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
    }

    private fun initRecyclerView() {
        val dummyData = listOf("기찬", "다희", "준하", "동훈")

        // 결제자
        addSpendAdapter = AddSpendAdapter(dummyData, singleSelection = true)
        binding.rvPayer.apply {
            adapter = addSpendAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        // 참여자
        val participantAdapter = AddSpendAdapter(dummyData)
        binding.rvParticipants.apply {
            adapter = participantAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        // 개별 금액 입력용
        val individualAdapter = AddSpendIndividualAdapter(dummyData) { totalPrice ->
            binding.tvAddSpendIndividualTotalPrice.text = totalPrice.toString()
        }
        binding.rvIndividual.adapter = individualAdapter
        binding.rvIndividual.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setPaymentMethod(isSplit: Boolean) {
        if (isSplit) {
            // n빵
            binding.tvAddSpendSplit.setBackgroundResource(R.drawable.shape_rect_999_blue_main_fill)
            binding.tvAddSpendIndividual.setBackgroundResource(R.drawable.shape_rect_999_gray300_fill)
            binding.clAddSpendSplit.visibility = View.VISIBLE
            binding.clAddSpendIndividual.visibility = View.GONE
        } else {
            // 각자
            binding.tvAddSpendSplit.setBackgroundResource(R.drawable.shape_rect_999_gray300_fill)
            binding.tvAddSpendIndividual.setBackgroundResource(R.drawable.shape_rect_999_blue_main_fill)
            binding.clAddSpendSplit.visibility = View.GONE
            binding.clAddSpendIndividual.visibility = View.VISIBLE
        }
    }

}