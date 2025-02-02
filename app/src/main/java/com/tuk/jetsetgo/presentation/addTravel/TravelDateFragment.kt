package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelDateBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelDateFragment: BaseFragment<FragmentTravelDateBinding>(R.layout.fragment_travel_date) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.viewTravelDateConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToLocation)
        }
    }
}