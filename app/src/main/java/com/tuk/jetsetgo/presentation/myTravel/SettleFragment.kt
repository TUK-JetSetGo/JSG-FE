package com.tuk.jetsetgo.presentation.myTravel

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSettleBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment

class SettleFragment : BaseFragment<FragmentSettleBinding>(R.layout.fragment_settle) {

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