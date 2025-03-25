package com.tuk.jetsetgo.presentation.addTravel

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelStartPointBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.StartPointAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelStartPointFragment : BaseFragment<FragmentTravelStartPointBinding>(R.layout.fragment_travel_start_point) {
    private lateinit var startPointAdapter: StartPointAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addTravelViewModel: AddTravelViewModel by viewModels()

    override fun initObserver() {
    }

    override fun initView() {
        initRecyclerView()
        setupConfirmButton()
    }

    private fun initRecyclerView() {
        startPointAdapter = StartPointAdapter(itemCount = 4) { position ->
            findNavController().navigate(R.id.goToMap)
        }
        binding.rvTravelStartPoint.adapter = startPointAdapter
    }



    private fun setupConfirmButton() {
        binding.clTravelStartPointConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToLoading)
        }
    }
}
