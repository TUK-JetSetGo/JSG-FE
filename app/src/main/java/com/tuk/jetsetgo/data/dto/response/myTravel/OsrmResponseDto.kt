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
    ) {
        fun toWaypointModel() =
            OsrmResponseModel.WaypointModel(hint, distance, name, location)
    }

    data class RouteDto(
        val distance: Double,         // 전체 경로 거리(미터)
        val duration: Double,         // 전체 소요 시간(초)
        val weight: Double,           // 내부 가중치(보통 duration)
        val weight_name: String?,     // 사용된 가중치 이름
        val geometry: GeometryDto,    // 경로 궤적 정보
        val legs: List<RouteLegDto>   // 웨이포인트 사이 구간 정보
    )

    data class GeometryDto(
        val type: String,                     // "LineString"
        val coordinates: List<List<Double>>   // [[lon, lat], [lon, lat], ...]
    )

    data class RouteLegDto(
        val distance: Double,                 // 이 구간 거리(미터)
        val duration: Double,                 // 이 구간 소요 시간(초)
        val summary: String?,                 // 주요 도로명 요약
        val steps: List<RouteStepDto>?        // steps=true 요청 시 제공되는 상세 지침
    )

    data class RouteStepDto(
        val distance: Double,
        val duration: Double,
        val geometry: GeometryDto,
        val name: String,
        val maneuver: ManeuverDto
    )

    data class ManeuverDto(
        val location: List<Double>,   // [lon, lat]
        val type: String,             // turn, depart, arrive 등
        val modifier: String?         // left, right, straight 등
    )
}

