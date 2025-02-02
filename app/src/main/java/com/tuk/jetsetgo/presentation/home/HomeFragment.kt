package com.tuk.jetsetgo.presentation.home

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentHomeBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initObserver() {
    }

    override fun initView() {
        bottomNavigationVisible()
    }

    private fun bottomNavigationVisible() {
        // BottomNavigationView 보이기
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }

}