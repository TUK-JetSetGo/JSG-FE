package com.tuk.jetsetgo.data.dto.response.myTravel

data class OsrmResponseDto(
    val code: String,
    val waypoints: List<WaypointDto>?,
    val routes: List<RouteDto>
) {

    data class WaypointDto(
        val hint: String?,            // 다음 요청을 위한 힌트 문자열
        val distance: Double?,        // 입력 좌표와 스냅된 지점 사이 거리(미터)
        val name: String?,            // 스냅된 도로명
        val location: List<Double>    // [longitude, latitude]
    )

    data class RouteDto(
        val geometry: String,
        val legs: List<RouteLegDto>,  // 구간별 정보
        val summary: String?,         // 주요 도로명 요약 (예: "불정로, 정자로")
        val weight_name: String?,     // 사용된 가중치 이름 (예: "routability")
        val weight: Double,           // 전체 경로 가중치
        val duration: Double,         // 전체 경로 소요 시간 (초)
        val distance: Double          // 전체 경로 거리 (미터)
    )

    data class RouteLegDto(
        val steps: List<RouteStepDto>? // 세부 단계별 지침
    )

    data class RouteStepDto(
        val geometry: String,         // 인코딩된 polyline 문자열
        val maneuver: ManeuverDto,    // 회전 지점 및 방향 정보
        val mode: String,             // 모드 (예: "driving")
        val driving_side: String,     // 주행 차선 (예: "right")
        val name: String,             // 해당 턴이 일어나는 도로명
        val intersections: List<IntersectionDto>,
        val weight: Double,           // 이 단계 가중치
        val duration: Double,         // 이 단계 소요 시간
        val distance: Double          // 이 단계 거리
    )

    data class ManeuverDto(
        val bearing_after: Int,       // 회전 후 방향 (도)
        val bearing_before: Int,      // 회전 전 방향 (도)
        val location: List<Double>,   // [longitude, latitude]
        val modifier: String?,        // 회전 방향 (예: "left", "right")
        val type: String              // 동작 타입 (예: "depart", "turn", "arrive")
    )

    data class IntersectionDto(
        val out: Int?,                // 이동할 방면 인덱스
        val `in`: Int?,               // 진입한 방면 인덱스
        val entry: List<Boolean>,     // 진입 가능 여부 배열
        val bearings: List<Int>,      // 가능한 방위 배열
        val location: List<Double>    // [longitude, latitude]
    )
}
