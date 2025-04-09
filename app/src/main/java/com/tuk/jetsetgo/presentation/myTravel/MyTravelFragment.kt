package com.tuk.jetsetgo.presentation.myTravel

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMyTravelBinding
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.TravelAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyTravelFragment : BaseFragment<FragmentMyTravelBinding>(R.layout.fragment_my_travel) {
    private lateinit var travelAdapter: TravelAdapter
    private val viewModel: MyTravelViewModel by activityViewModels()

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myTravelList.collectLatest { travelList ->
                    travelAdapter.submitList(travelList)
                    updateTravelVisibility(travelList)
                }
            }
        }
    }

    override fun initView() {
        setClickListener()
        initRecyclerView()
        viewModel.fetchMyTravelList()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setClickListener() {
        
    }

    private fun initRecyclerView() {
        binding.rvTravelLocation.layoutManager = LinearLayoutManager(requireContext())
        travelAdapter = TravelAdapter { travelList ->
            // 클릭 시 여행 상세 화면으로 이동
            viewModel.setTravelPlanId(travelList.travelPlanId)
            findNavController().navigate(R.id.goToSchedule)
        }
        binding.rvTravelLocation.adapter = travelAdapter
    }

    private fun updateTravelVisibility(travelList: List<MyPlanResponseModel.MyTravelPlanInfoListModel>) {
        if (travelList.isEmpty()) {
            binding.tvMyTravelNothing.visibility = View.VISIBLE
            binding.rvTravelLocation.visibility = View.GONE
        } else {
            binding.tvMyTravelNothing.visibility = View.GONE
            binding.rvTravelLocation.visibility = View.VISIBLE
        }
    }

}