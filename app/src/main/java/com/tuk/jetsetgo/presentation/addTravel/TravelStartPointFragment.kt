package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelStartPointBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.StartPointAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TravelStartPointFragment : BaseFragment<FragmentTravelStartPointBinding>(R.layout.fragment_travel_start_point) {
    private lateinit var startPointAdapter: StartPointAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addTravelViewModel: AddTravelViewModel by viewModels()

    private val days: Int? by lazy {
        arguments?.getInt("days")
    }

    override fun initObserver() {
    }

    override fun initView() {
        val days = days ?: return
        Log.d("TravelStartPointFragment", "여행일수: ${days}일")

        sharedViewModel.setDailyStartPointName(List(days) { "" })
        sharedViewModel.setDailyStartPointList(List(days) { 0 })

        initRecyclerView()
        setupConfirmButton()
    }

    private fun initRecyclerView() {
        val days = days ?: return

        binding.rvTravelStartPoint.layoutManager = LinearLayoutManager(requireContext())
        startPointAdapter = StartPointAdapter(itemCount = days) { position ->
            val action = TravelStartPointFragmentDirections.goToMap(lastFragmentId = 1)
            findNavController().navigate(action)
        }
        binding.rvTravelStartPoint.adapter = startPointAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.dailyStartPointName.collectLatest { names ->
                startPointAdapter.submitStartPointNames(names)
            }
        }
    }



    private fun setupConfirmButton() {
        binding.clTravelStartPointConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToLocation)
        }
    }
}
