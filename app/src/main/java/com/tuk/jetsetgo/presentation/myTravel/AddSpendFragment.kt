package com.tuk.jetsetgo.presentation.myTravel

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentAddSpendBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.SpendAdapter

class AddSpendFragment : BaseFragment<FragmentAddSpendBinding>(R.layout.fragment_add_spend) {

    private lateinit var spendAdapter: SpendAdapter


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

    }
}