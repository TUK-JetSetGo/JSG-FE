package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelThemeBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelThemeFragment:BaseFragment<FragmentTravelThemeBinding>(R.layout.fragment_travel_theme) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.viewTravelThemeConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToBudget)
        }
    }
}