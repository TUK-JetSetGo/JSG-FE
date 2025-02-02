package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelLoadingBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelLoadingFragment: BaseFragment<FragmentTravelLoadingBinding>(R.layout.fragment_travel_loading) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.viewTravelLoadingHomeBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToHome)
        }
    }
}