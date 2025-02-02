package com.tuk.jetsetgo.presentation.addTravel

import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelPersonnelBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelPersonnelFragment : BaseFragment<FragmentTravelPersonnelBinding>(R.layout.fragment_travel_personnel) {
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
        binding.viewTravelPersonnelConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToCountry)
        }
    }
}