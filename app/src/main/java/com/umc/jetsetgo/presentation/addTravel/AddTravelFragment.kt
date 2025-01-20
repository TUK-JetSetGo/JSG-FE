package com.umc.jetsetgo.presentation.addTravel

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umc.jetsetgo.R
import com.umc.jetsetgo.databinding.FragmentAddTravelBinding
import com.umc.jetsetgo.presentation.base.BaseFragment

class AddTravelFragment : BaseFragment<FragmentAddTravelBinding>(R.layout.fragment_add_travel) {
    override fun initObserver() {

    }

    override fun initView() {
        bottomNavigationRemove()
    }

    private fun bottomNavigationRemove() {
        // BottomNavigationView 숨기기
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }
}