package com.tuk.jetsetgo.presentation.myTravel.adapter

import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto

enum class RouteLabel { FASTEST, LEAST_TRANSFER, LEAST_WALK }

data class RouteItem(
    val label: RouteLabel?,
    val path: OdsayRouteDto.ResultDto.PathDto,
    var isExpanded: Boolean = false
)

data class RouteDetailItem(
    val id: String,          // DiffUtil용 고유키
    val type: Int,           // 1=지하철, 2=버스, 3=도보 (ODsay 그대로)
    val title: String,       // "도보", "버스 510" 등
    val place: String,       // "A 정류장 → B 정류장" 또는 "출발지 → 환승지" 스타일
    val timeText: String,    // "n정거장 • 약 n분" / "도보 350m • 약 6분"
    val iconRes: Int         // R.drawable.ic_walk / ic_bus / ic_subway
)

// OdsayRouteDto.Result.Path 확장함수: 상세 리스트로 변환
fun OdsayRouteDto.ResultDto.PathDto.toDetailItems(): List<RouteDetailItem> {
    val items = mutableListOf<RouteDetailItem>()

    fun fmtMin(min: Int?) = if (min == null) "" else "약 ${min}분"
    fun fmtWalk(m: Int?) = if (m == null) "" else "도보 ${m}m"

    subPath.forEachIndexed { idx, sp ->
        when (sp.trafficType) {
            // --- 도보 ---
            3 -> {
                val meters = sp.distance ?: 0
                val minutes = sp.sectionTime ?: 0

                // 앞/뒤 구간(대중교통) 참고해서 place 생성
                val prevTransit = subPath.take(idx).lastOrNull { it.trafficType != 3 }
                val nextTransit = subPath.drop(idx + 1).firstOrNull { it.trafficType != 3 }

                val place = when {
                    // 첫 구간: 출발지 -> 다음 환승지(정류장/역)
                    idx == 0 && nextTransit != null ->
                        "출발지 → ${nextTransit.startName ?: "승차 지점"}"

                    // 마지막 구간: 이전 하차 정류장/역 -> 도착지
                    idx == subPath.lastIndex && prevTransit != null ->
                        "${prevTransit.endName ?: "하차 지점"} → 도착지"

                    // 중간 환승 이동: 이전 하차 → 다음 승차
                    prevTransit != null && nextTransit != null ->
                        "${prevTransit.endName ?: "하차 지점"} → ${nextTransit.startName ?: "승차 지점"}"

                    // 정보가 부족하면 기본 문구
                    else -> "이동"
                }

                items += RouteDetailItem(
                    id = "walk-$idx-$meters-$minutes",
                    type = 3,
                    title = "도보",
                    place = place,
                    timeText = "${fmtWalk(meters)} • ${fmtMin(minutes)}",
                    iconRes = R.drawable.ic_walk
                )
            }

            // --- 버스 ---
            2 -> {
                val minutes = sp.sectionTime ?: 0
                val stations = sp.stationCount ?: 0
                val start = sp.startName ?: "출발 정류장"
                val end = sp.endName ?: "도착 정류장"
                val busNo = sp.lane?.firstOrNull()?.busNo ?: "버스"

                items += RouteDetailItem(
                    id = "bus-$idx-$busNo-$start-$end",
                    type = 2,
                    title = "버스 $busNo",
                    place = "$start → $end",
                    timeText = "${stations}정거장 • ${fmtMin(minutes)}",
                    iconRes = R.drawable.ic_bus
                )
            }

            // --- 지하철(참고용) ---
            1 -> {
                val minutes = sp.sectionTime ?: 0
                val stations = sp.stationCount ?: 0
                val start = sp.startName ?: "출발 역"
                val end = sp.endName ?: "도착 역"

                // ODsay의 lane에는 노선명/호선 번호 대신 객체가 오므로 필요 시 필드 맞춰서 수정
                val lineName = sp.lane?.firstOrNull()?.busNo ?: "지하철"

                items += RouteDetailItem(
                    id = "subway-$idx-$lineName-$start-$end",
                    type = 1,
                    title = lineName,                      // 예: "4호선"
                    place = "$start → $end",
                    timeText = "${stations}역 • ${fmtMin(minutes)}",
                    iconRes = R.drawable.ic_metro
                )
            }
        }
    }
    return items
}
