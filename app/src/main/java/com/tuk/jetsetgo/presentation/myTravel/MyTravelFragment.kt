package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.umc.jetsetgo.R
import com.umc.jetsetgo.databinding.FragmentMyTravelBinding


class MyTravelFragment : BaseFragment<FragmentMyTravelBinding>(R.layout.fragment_my_travel) {
    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setClickListener() {
        
    }

}