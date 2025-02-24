package com.tuk.jetsetgo.presentation.addTravel

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelActivityLevelBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TravelActivityLevelFragment: BaseFragment<FragmentTravelActivityLevelBinding>(R.layout.fragment_travel_activity_level) {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var startBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var endBottomSheetBehavior: BottomSheetBehavior<View>

    override fun initObserver() {
        setClickListener()
        initBottomSheets()
        initBottomSheetActions()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        setupConfirmButton()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupConfirmButton() {
        binding.viewActivityLevelConfirmBtn.setOnSingleClickListener {
            val startTimeFull = binding.tvActivityLevelTimeStart.text.toString()
            val endTimeFull = binding.tvActivityLevelTimeEnd.text.toString()

            val startTimeStr = if (startTimeFull.length >= 5) startTimeFull.substring(0, 5) else startTimeFull
            val endTimeStr = if (endTimeFull.length >= 5) endTimeFull.substring(0, 5) else endTimeFull

            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val startTime = LocalTime.parse(startTimeStr, formatter)
            val endTime = LocalTime.parse(endTimeStr, formatter)

            if (startTime.isBefore(endTime)) {
                findNavController().navigate(R.id.goToPurpose)
            } else {
                Toast.makeText(requireContext(), "활동 시작 시간은 종료 시간보다 빨라야 합니다", Toast.LENGTH_SHORT).show()
            }
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
            val hour = binding.pickerTimeStart.hour
            val minute = binding.pickerTimeStart.minute
            val startTime = String.format("%02d:%02d", hour, minute)
            val startTimeVM = String.format("%02d:%02d:00", hour, minute)
            binding.tvActivityLevelTimeStart.text = startTime
            sharedViewModel.setActivityStartTime(startTimeVM)
            Log.d("TravelActivityLevelFragment", "시작 시간: ${sharedViewModel.activityStartTime.value}")
            startBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.tvActivityLevelEndSaveBtn.setOnClickListener {
            val hour = binding.pickerTimeEnd.hour
            val minute = binding.pickerTimeEnd.minute
            val endTime = String.format("%02d:%02d", hour, minute)
            val endTimeVM = String.format("%02d:%02d:00", hour, minute)
            binding.tvActivityLevelTimeEnd.text = endTime
            sharedViewModel.setActivityEndTime(endTimeVM)
            Log.d("TravelActivityLevelFragment", "종료 시간: ${sharedViewModel.activityEndTime.value}")
            endBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }


}