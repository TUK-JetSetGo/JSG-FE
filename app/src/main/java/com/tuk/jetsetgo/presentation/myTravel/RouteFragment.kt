package com.tuk.jetsetgo.presentation.myTravel

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentRouteBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class RouteFragment: BaseFragment<FragmentRouteBinding>(R.layout.fragment_route) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val odsayViewModel: OdsayViewModel by viewModels()

    override fun initObserver() {
        odsayViewModel.routeResult.observe(viewLifecycleOwner) { result ->
            result
                .onSuccess { dto ->
                    // TODO: 여기서 RecyclerView에 카드/상세 리스트 바인딩하거나 지도 그리기
                    // ex) renderRoutes(dto)
                }
                .onFailure { e ->
                    Toast.makeText(requireContext(), e.message ?: "길찾기 실패", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun initView() {
        setBackPressedCallback()
        callOdsayIfReady()
    }

    private fun callOdsayIfReady() {
        // SharedViewModel에 저장된 좌표 사용
        val sx = sharedViewModel.startLon.value  // 경도
        val sy = sharedViewModel.startLat.value  // 위도
        val ex = sharedViewModel.endLon.value
        val ey = sharedViewModel.endLat.value

        if (sx == null || sy == null || ex == null || ey == null) {
            Toast.makeText(requireContext(), "출발/도착 지점을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 옵션은 필요 시 조정 (0=추천)
        odsayViewModel.loadRoute(
            sx = sx, sy = sy,
            ex = ex, ey = ey,
            opt = 0, lang = 0
        )
    }

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
    }
}