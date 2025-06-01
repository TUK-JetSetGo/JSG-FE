package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentModifyReasonBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment

class ModifyReasonFragment : BaseFragment<FragmentModifyReasonBinding>(R.layout.fragment_modify_reason) {

    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        setBackPressedCallback()
        setRadioGroupListener()
    }

    private fun setClickListener() {
        binding.ivModifyReasonBack.setOnClickListener { findNavController().popBackStack() }
        binding.viewModifyReasonNextBtn.setOnClickListener { findNavController().navigate(R.id.reasonToModifySchedule) }
    }

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
    }

    private fun setRadioGroupListener() {
        binding.rgModifyReason.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                binding.viewModifyReasonNextBtn.visibility = View.VISIBLE
            }
        }
    }

}