package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelLocationBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelLocationFragment: BaseFragment<FragmentTravelLocationBinding>(R.layout.fragment_travel_location) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }


    private fun setupConfirmButton() {
        binding.viewTravelLocationSaveBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToLoading)
        }
    }
}