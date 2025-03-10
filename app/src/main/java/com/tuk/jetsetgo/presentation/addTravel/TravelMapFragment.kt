package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelLocationBinding
import com.tuk.jetsetgo.databinding.FragmentTravelMapBinding
import com.tuk.jetsetgo.databinding.FragmentTravelSearchBinding
import com.tuk.jetsetgo.domain.model.request.addTravel.CreatePlanRequestModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.TravelLocationAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TravelMapFragment : BaseFragment<FragmentTravelMapBinding>(R.layout.fragment_travel_map) {
    private lateinit var travelLocationAdapter: TravelLocationAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addTravelViewModel: AddTravelViewModel by viewModels()

    override fun initObserver() {
    }

    override fun initView() {
        //initRecyclerView()
    }

    /*
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
*/

}
