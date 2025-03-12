package com.tuk.jetsetgo.presentation.addTravel

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelDateBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TravelDateFragment: BaseFragment<FragmentTravelDateBinding>(R.layout.fragment_travel_date) {
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
        binding.viewTravelDateConfirmBtn.setOnSingleClickListener {
            val startDateStr = binding.tvTravelDateSetStartDate.text.toString()
            val endDateStr = binding.tvTravelDateSetEndDate.text.toString()

            // 날짜가 선택되지 않았으면 토스트 메시지 표시 후 종료
            if (startDateStr.isBlank() || endDateStr.isBlank()) {
                Toast.makeText(requireContext(), "여행 날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            try {
                val startDate = java.time.LocalDate.parse(startDateStr, formatter)
                val endDate = java.time.LocalDate.parse(endDateStr, formatter)
                if (endDate.isAfter(startDate)) {
                    findNavController().navigate(R.id.goToLocation)
                } else {
                    Toast.makeText(requireContext(), "여행 종료 날짜는 시작 날짜보다 이후여야 합니다", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "날짜 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
            }
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
            val day = binding.pickerDateStart.dayOfMonth
            val month = binding.pickerDateStart.month + 1
            val year = binding.pickerDateStart.year
            val startDate = String.format("%04d-%02d-%02d", year, month, day)
            binding.tvTravelDateSetStartDate.text = startDate
            sharedViewModel.setTravelStartDate(startDate)
            Log.d("TravelDateFragment", "시작 날짜: ${sharedViewModel.travelStartDate.value}")
            startBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.tvTravelDateEndSaveBtn.setOnClickListener {
            val day = binding.pickerDateEnd.dayOfMonth
            val month = binding.pickerDateEnd.month + 1
            val year = binding.pickerDateEnd.year
            val endDate = String.format("%04d-%02d-%02d", year, month, day)
            binding.tvTravelDateSetEndDate.text = endDate
            sharedViewModel.setTravelEndDate(endDate)
            Log.d("TravelDateFragment", "종료 날짜: ${sharedViewModel.travelEndDate.value}")
            endBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}