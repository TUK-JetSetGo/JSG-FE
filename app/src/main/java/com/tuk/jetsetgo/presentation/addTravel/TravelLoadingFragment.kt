package com.tuk.jetsetgo.presentation.addTravel

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelLoadingBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TravelLoadingFragment : BaseFragment<FragmentTravelLoadingBinding>(R.layout.fragment_travel_loading) {
    override fun initObserver() {}

    override fun initView() {
        // 2초 후 자동 이동
        navigateToCompleteWithDelay()
    }

    private fun navigateToCompleteWithDelay() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000L) // 2초 대기
            findNavController().navigate(R.id.goToComplete)
        }
    }
}
