package com.tuk.jetsetgo.presentation.addTravel

import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import com.umc.jetsetgo.R
import com.umc.jetsetgo.databinding.FragmentTravelBudgetBinding

class TravelBudgetFragment: BaseFragment<FragmentTravelBudgetBinding>(R.layout.fragment_travel_budget) {
    override fun initObserver() {

    }

    override fun initView() {
        bottomNavigationRemove()
        setupConfirmButton()
    }

    private fun bottomNavigationRemove() {
        // BottomNavigationView 숨기기
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.GONE
    }

    private fun setupConfirmButton() {
        binding.viewConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToDate)
        }
    }
}