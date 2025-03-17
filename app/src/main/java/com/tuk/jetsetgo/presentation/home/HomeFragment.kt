package com.tuk.jetsetgo.presentation.home

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentHomeBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.home.adapter.PictureAdapter

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var pictureAdapter: PictureAdapter
    private val pictureList = mutableListOf("https://image1.jpg", "https://image1.jpg","https://image1.jpg","https://image1.jpg",)

    override fun initObserver() {
    }

    override fun initView() {
        bottomNavigationVisible()
        initRecyclerView()
    }

    private fun bottomNavigationVisible() {
        // BottomNavigationView 보이기
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.rvHomeAiRecommendPicture.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        pictureAdapter = PictureAdapter(pictureList)
        binding.rvHomeAiRecommendPicture.adapter = pictureAdapter
    }


}