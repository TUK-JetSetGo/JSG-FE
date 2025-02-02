package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelCountryBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelCountryFragment: BaseFragment<FragmentTravelCountryBinding>(R.layout.fragment_travel_country) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.viewTravelCountryConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToActivityLevel)
        }
    }
}