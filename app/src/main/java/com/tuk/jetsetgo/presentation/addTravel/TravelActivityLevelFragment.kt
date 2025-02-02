package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelActivityLevelBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelActivityLevelFragment: BaseFragment<FragmentTravelActivityLevelBinding>(R.layout.fragment_travel_activity_level) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.viewActivityLevelConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToPurpose)
        }
    }

}