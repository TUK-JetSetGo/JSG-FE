package com.tuk.jetsetgo.presentation.addTravel

import android.view.View
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelDateBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelDateFragment: BaseFragment<FragmentTravelDateBinding>(R.layout.fragment_travel_date) {

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
        binding.viewTravelDateConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToLocation)
        }
    }

    private fun setClickListener() {
        binding.tvTravelDateSetStartDate.setOnClickListener { toggleBottomSheetState(startBottomSheetBehavior) }
        binding.tvTravelDateSetEndDate.setOnClickListener { toggleBottomSheetState(endBottomSheetBehavior) }
    }

    private fun initBottomSheets() {
        startBottomSheetBehavior = createBottomSheet(binding.clBottomSheetDateStart)
        endBottomSheetBehavior = createBottomSheet(binding.clBottomSheetDateEnd)
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
                        binding.clBottomSheetDateStart.id -> {
                            binding.ivTravelDateStartDateArrow.setBackgroundResource(
                                if (newState == BottomSheetBehavior.STATE_HIDDEN) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
                            )
                        }
                        binding.clBottomSheetDateEnd.id -> {
                            binding.ivTravelDateEndDateArrow.setBackgroundResource(
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
        binding.viewTravelDateBottomSheetBg.visibility = if (newState == BottomSheetBehavior.STATE_HIDDEN) View.GONE else View.VISIBLE
    }

    private fun toggleBottomSheetState(bottomSheetBehavior: BottomSheetBehavior<View>) {
        bottomSheetBehavior.state = if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initBottomSheetActions() {
        binding.tvTravelDateStartSaveBtn.setOnClickListener {
            // binding.tvTravelDateSetStartDate.text = startDate
            startBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.tvTravelDateEndSaveBtn.setOnClickListener {
            // binding.tvTravelDateSetEndDate.text = endDate
            endBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}