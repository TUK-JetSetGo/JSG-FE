package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelLoadingBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TravelLoadingFragment : BaseFragment<FragmentTravelLoadingBinding>(R.layout.fragment_travel_loading) {
    private val addTravelViewModel: AddTravelViewModel by activityViewModels()

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addTravelViewModel.createTravelState.collectLatest { state ->
                    when (state) {
                        is UiState.Success -> {
                            Log.d("TravelLoadingFragment", "일정 생성 완료됨 → 다음 화면으로 이동")
                            findNavController().navigate(R.id.goToComplete)
                        }
                        is UiState.Error -> {
                            Log.e("TravelLoadingFragment", "일정 생성 실패 → 다시 이전 화면?")
                            Toast.makeText(requireContext(), "일정 생성 실패", Toast.LENGTH_SHORT).show()
                            // 필요하면 이전 화면으로 popBackStack()
                        }
                        else -> {
                            // 로딩 중 → 로딩 애니메이션 유지
                        }
                    }
                }
            }
        }
    }

    override fun initView() {

    }
}
