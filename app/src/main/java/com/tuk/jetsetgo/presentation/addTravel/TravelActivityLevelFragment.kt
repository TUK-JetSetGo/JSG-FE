package com.tuk.jetsetgo.presentation.addTravel

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelActivityLevelBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.ThemeAdapter
import com.tuk.jetsetgo.presentation.addTravel.adapter.TransportationAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TravelActivityLevelFragment: BaseFragment<FragmentTravelActivityLevelBinding>(R.layout.fragment_travel_activity_level) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var transportationAdapter: TransportationAdapter

    private lateinit var startBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var endBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var transportationBottomSheetBehavior: BottomSheetBehavior<View>

    override fun initObserver() {
        setClickListener()
        initBottomSheets()
        initBottomSheetActions()
        initVisitCountControls()
        setupTransportationRecyclerView()
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

            // 시간이 선택되지 않았으면 토스트 메시지 표시 후 종료
            if (startTimeFull.isBlank() || endTimeFull.isBlank()) {
                Toast.makeText(requireContext(), "활동 시간을 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val startTimeStr = if (startTimeFull.length >= 5) startTimeFull.substring(0, 5) else startTimeFull
            val endTimeStr = if (endTimeFull.length >= 5) endTimeFull.substring(0, 5) else endTimeFull

            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val startTime = LocalTime.parse(startTimeStr, formatter)
            val endTime = LocalTime.parse(endTimeStr, formatter)

            if (startTime.isBefore(endTime)) {
                val visitCount = binding.tvActivityLevelVisitCount.text.toString().toIntOrNull() ?: 1
                sharedViewModel.setDailyVisitCount(visitCount)
                Log.d("TravelActivityLevelFragment", "방문지 개수: ${sharedViewModel.dailyVisitCount.value}")
                Log.d("TravelActivityLevelFragment", "시작 시간: ${sharedViewModel.activityStartTime.value}")
                Log.d("TravelActivityLevelFragment", "종료 시간: ${sharedViewModel.activityEndTime.value}")
                Log.d("TravelActivityLevelFragment", "이동 수단: ${sharedViewModel.preferredTransport.value}")
                findNavController().navigate(R.id.goToPurpose)
            } else {
                Toast.makeText(requireContext(), "활동 시작 시간은 종료 시간보다 빨라야 합니다", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setClickListener() {
        binding.tvActivityLevelTimeStart.setOnSingleClickListener { toggleBottomSheetState(startBottomSheetBehavior) }
        binding.tvActivityLevelTimeEnd.setOnSingleClickListener { toggleBottomSheetState(endBottomSheetBehavior) }
        binding.tvActivityLevelDropDownTransportation.setOnSingleClickListener { toggleBottomSheetState(transportationBottomSheetBehavior) }
    }

    private fun setupTransportationRecyclerView() {
        transportationAdapter = TransportationAdapter { selected ->
            // 한글 → 영문 코드로 매핑
            val code = when (selected) {
                "자동차" -> "CAR"
                "도보" -> "WALK"
                "자전거" -> "BIKE"
                "대중교통" -> "PUBLIC_TRANSPORT"
                else -> ""
            }

            // UI 업데이트
            binding.tvActivityLevelDropDownTransportation.text = selected

            // ViewModel 업데이트
            sharedViewModel.setPreferredTransport(code)

            // 바텀시트 닫기
            transportationBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.rvTheme.adapter = transportationAdapter
        binding.rvTheme.layoutManager = LinearLayoutManager(requireContext())

        val transportationList = listOf("자동차", "도보", "자전거", "대중교통")
        transportationAdapter.updateList(transportationList)
    }


    private fun initBottomSheets() {
        startBottomSheetBehavior = createBottomSheet(binding.clBottomSheetTimeStart)
        endBottomSheetBehavior = createBottomSheet(binding.clBottomSheetTimeEnd)
        transportationBottomSheetBehavior = createBottomSheet(binding.llBottomSheetTransportation)
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
            startBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.tvActivityLevelEndSaveBtn.setOnClickListener {
            val hour = binding.pickerTimeEnd.hour
            val minute = binding.pickerTimeEnd.minute
            val endTime = String.format("%02d:%02d", hour, minute)
            val endTimeVM = String.format("%02d:%02d:00", hour, minute)
            binding.tvActivityLevelTimeEnd.text = endTime
            sharedViewModel.setActivityEndTime(endTimeVM)
            endBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    // 방문할 관광지 개수 컨트롤
    private fun initVisitCountControls() {
        binding.ivActivityLevelPlus.setOnClickListener {
            var count = binding.tvActivityLevelVisitCount.text.toString().toIntOrNull() ?: 1
            if (count < 5) {
                count++
                binding.tvActivityLevelVisitCount.text = count.toString()
                updateVisitCountButtonTints(count)
            }
        }
        binding.ivActivityLevelMinus.setOnClickListener {
            var count = binding.tvActivityLevelVisitCount.text.toString().toIntOrNull() ?: 1
            if (count > 1) {
                count--
                binding.tvActivityLevelVisitCount.text = count.toString()
                updateVisitCountButtonTints(count)
            }
        }
    }

    private fun updateVisitCountButtonTints(count: Int) {
        if (count == 1) {
            binding.ivActivityLevelMinus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.gray_400)
            binding.ivActivityLevelPlus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.black)
        } else if (count == 5) {
            binding.ivActivityLevelPlus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.gray_400)
            binding.ivActivityLevelMinus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.black)
        } else {
            binding.ivActivityLevelPlus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.black)
            binding.ivActivityLevelMinus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.black)
        }
    }
}