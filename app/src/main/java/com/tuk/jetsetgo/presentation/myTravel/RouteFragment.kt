package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto
import com.tuk.jetsetgo.databinding.FragmentRouteBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.RouteItem
import com.tuk.jetsetgo.presentation.myTravel.adapter.RouteLabel
import com.tuk.jetsetgo.presentation.myTravel.adapter.RouteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class RouteFragment: BaseFragment<FragmentRouteBinding>(R.layout.fragment_route) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val odsayViewModel: OdsayViewModel by viewModels()
    private lateinit var routeListAdapter: RouteListAdapter

    override fun initObserver() {
        odsayViewModel.routeResult.observe(viewLifecycleOwner) { result ->
            result
                .onSuccess { dto ->
                    val items = makeAllRouteItems(dto)
                    routeListAdapter.submitList(items)
                    binding.layoutRouteError.visibility = View.GONE

                }
                .onFailure { e ->
                    Toast.makeText(requireContext(), e.message ?: "길찾기 실패", Toast.LENGTH_SHORT).show()
                    binding.layoutRouteError.visibility = View.VISIBLE
                }
        }
    }

    override fun initView() {
        setBackPressedCallback()
        callOdsayIfReady()
        setupRecycler()
    }

    private fun setupRecycler() {
        routeListAdapter = RouteListAdapter()
        binding.rvRouteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = routeListAdapter
            setHasFixedSize(true)
        }
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

    private fun makeRouteItems(dto: OdsayRouteDto): List<RouteItem> {
        val paths = dto.result?.path.orEmpty()
        if (paths.isEmpty()) return emptyList()

        fun OdsayRouteDto.ResultDto.PathDto.transferCount() =
            (info?.busTransitCount ?: 0) + (info?.subwayTransitCount ?: 0)

        val fastest = paths.minByOrNull { it.info?.totalTime ?: Int.MAX_VALUE }
        val leastTransfer = paths.minWithOrNull(
            compareBy<OdsayRouteDto.ResultDto.PathDto> { it.transferCount() }
                .thenBy { it.info?.totalTime ?: Int.MAX_VALUE }
        )
        val leastWalk = paths.minWithOrNull(
            compareBy<OdsayRouteDto.ResultDto.PathDto> { it.info?.totalWalk ?: Int.MAX_VALUE }
                .thenBy { it.info?.totalTime ?: Int.MAX_VALUE }
        )

        val list = listOfNotNull(
            fastest?.let { RouteItem(RouteLabel.FASTEST, it) },
            leastTransfer?.let { RouteItem(RouteLabel.LEAST_TRANSFER, it) },
            leastWalk?.let { RouteItem(RouteLabel.LEAST_WALK, it) },
        )

        // 동일 경로 중복 제거 (시간/도보/환승 기준)
        return list.distinctBy {
            Triple(it.path.info?.totalTime, it.path.info?.totalWalk, it.path.transferCount())
        }
    }

    private fun makeAllRouteItems(dto: OdsayRouteDto): List<RouteItem> {
        val paths = dto.result?.path.orEmpty()
        if (paths.isEmpty()) return emptyList()

        // 최단 시간 path 찾기
        val sorted = paths.sortedBy { it.info.totalTime ?: Int.MAX_VALUE }
        val fastest = sorted.firstOrNull()

        return sorted.map { p ->
            RouteItem(
                label = if (p.info.mapObj == fastest?.info?.mapObj) RouteLabel.FASTEST else null,
                path = p
            )
        }
    }



    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
        })
        binding.ivRouteBack.setOnClickListener { findNavController().popBackStack() }
    }
}