package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMyTravelBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment

class MyTravelFragment : BaseFragment<FragmentMyTravelBinding>(R.layout.fragment_my_travel) {
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