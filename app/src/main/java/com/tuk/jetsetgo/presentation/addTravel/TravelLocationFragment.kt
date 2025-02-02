package com.tuk.jetsetgo.presentation.addTravel

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelLocationBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.TravelLocationAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelLocationFragment : BaseFragment<FragmentTravelLocationBinding>(R.layout.fragment_travel_location) {
    private lateinit var travelLocationAdapter: TravelLocationAdapter
    private val locationList = mutableListOf("장소1", "장소2", "장소3")
    override fun initObserver() {

    }

    override fun initView() {
        initRecyclerView()
        setupConfirmButton()
    }

    private fun initRecyclerView() {
        binding.rvTravelLocation.layoutManager = LinearLayoutManager(requireContext())
        travelLocationAdapter = TravelLocationAdapter(locationList) { position ->
            travelLocationAdapter.removeItem(position)
            toggleConfirmButtonVisibility()
        }
        binding.rvTravelLocation.adapter = travelLocationAdapter
        toggleConfirmButtonVisibility()
    }

    private fun toggleConfirmButtonVisibility() {
        binding.clTravelLocationConfirmBtn.visibility =
            if (locationList.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupConfirmButton() {
        binding.clTravelLocationConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToLoading)
        }
    }
}
