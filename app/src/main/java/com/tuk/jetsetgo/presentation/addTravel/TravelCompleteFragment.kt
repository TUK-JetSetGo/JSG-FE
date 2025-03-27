package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelCompleteBinding
import com.tuk.jetsetgo.databinding.FragmentTravelLoadingBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelCompleteFragment: BaseFragment<FragmentTravelCompleteBinding>(R.layout.fragment_travel_complete) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.tvTravelLoadingHomeBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToHome)
        }

        binding.tvTravelLoadingTravelBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToDetailSchedule)
        }
    }
}