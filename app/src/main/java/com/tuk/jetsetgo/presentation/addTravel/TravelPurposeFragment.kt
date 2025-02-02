package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelPurposeBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelPurposeFragment: BaseFragment<FragmentTravelPurposeBinding>(R.layout.fragment_travel_purpose) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }


    private fun setupConfirmButton() {
        binding.viewTravelPurposeConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToTheme)
        }
    }
}