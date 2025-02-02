package com.tuk.jetsetgo.presentation.addTravel

import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelCountryBinding

class TravelCountryFragment: BaseFragment<FragmentTravelCountryBinding>(R.layout.fragment_travel_country) {
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
        binding.viewTravelCountryConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToActivityLevel)
        }
    }
}