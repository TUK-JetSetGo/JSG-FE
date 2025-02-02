package com.tuk.jetsetgo.presentation.addTravel

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelBudgetBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelBudgetFragment: BaseFragment<FragmentTravelBudgetBinding>(R.layout.fragment_travel_budget) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.viewTravelBudgetConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToDate)
        }
    }
}