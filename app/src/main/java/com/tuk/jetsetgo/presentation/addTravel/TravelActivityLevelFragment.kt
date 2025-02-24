package com.tuk.jetsetgo.presentation.addTravel

import android.view.View
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelActivityLevelBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelActivityLevelFragment: BaseFragment<FragmentTravelActivityLevelBinding>(R.layout.fragment_travel_activity_level) {
    
    private lateinit var startBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var endBottomSheetBehavior: BottomSheetBehavior<View>

    override fun initObserver() {
        setClickListener()
        initBottomSheets()
        initBottomSheetActions()
    }

    override fun initView() {
        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.viewActivityLevelConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToPurpose)
        }
    }

    private fun setClickListener() {
        binding.tvActivityLevelTimeStart.setOnClickListener { toggleBottomSheetState(startBottomSheetBehavior) }
        binding.tvActivityLevelTimeEnd.setOnClickListener { toggleBottomSheetState(endBottomSheetBehavior) }
    }

    private fun initBottomSheets() {
        startBottomSheetBehavior = createBottomSheet(binding.clBottomSheetTimeStart)
        endBottomSheetBehavior = createBottomSheet(binding.clBottomSheetTimeEnd)
    }

    private fun createBottomSheet(sheet: View): BottomSheetBehavior<View> {
        return BottomSheetBehavior.from(sheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = 0
            isHideable = true
            skipCollapsed = true
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    handleBackgroundVisibility(newState)
                    when (sheet.id) {
                        binding.clBottomSheetTimeStart.id -> {
                            binding.ivActivityLevelStartArrow.setBackgroundResource(
                                if (newState == BottomSheetBehavior.STATE_HIDDEN) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
                            )
                        }
                        binding.clBottomSheetTimeEnd.id -> {
                            binding.ivActivityLevelEndArrow.setBackgroundResource(
                                if (newState == BottomSheetBehavior.STATE_HIDDEN) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
                            )
                        }
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }


    private fun handleBackgroundVisibility(newState: Int) {
        binding.viewActivityLevelBottomSheetBg.visibility = if (newState == BottomSheetBehavior.STATE_HIDDEN) View.GONE else View.VISIBLE
    }

    private fun toggleBottomSheetState(bottomSheetBehavior: BottomSheetBehavior<View>) {
        bottomSheetBehavior.state = if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initBottomSheetActions() {
        binding.tvActivityLevelStartSaveBtn.setOnClickListener {
            // binding.tvActivityLevelTimeStart.text = startTime
            startBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.tvActivityLevelEndSaveBtn.setOnClickListener {
            // binding.tvActivityLevelTimeEnd.text = endTime
            endBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

}