package com.tuk.jetsetgo.data.dto.response.myTravel

data class OdsayRouteDto(
    val result: ResultDto
) {
    data class ResultDto(
        val searchType: Int,
        val outTrafficCheck: Int,
        val busCount: Int,
        val subwayCount: Int,
        val subwayBusCount: Int,
        val pointDistance: Int,
        val startRadius: Int,
        val endRadius: Int,
        val path: List<PathDto>
    ) {
        data class PathDto(
            val pathType: Int,
            val info: InfoDto,
            val subPath: List<SubPathDto>
        ) {
            data class InfoDto(
                val trafficDistance: Int,
                val totalWalk: Int,
                val totalTime: Int,
                val payment: Int,
                val busTransitCount: Int,
                val subwayTransitCount: Int,
                val mapObj: String,
                val firstStartStation: String,
                val lastEndStation: String,
                val totalStationCount: Int,
                val busStationCount: Int,
                val subwayStationCount: Int,
                val totalDistance: Int,
                val totalWalkTime: Int,
                val checkIntervalTime: Int,
                val checkIntervalTimeOverYn: String,
                val totalIntervalTime: Int
            )

            data class SubPathDto(
                val trafficType: Int,           // 1=지하철, 2=버스, 3=도보
                val distance: Int? = null,
                val sectionTime: Int? = null,
                val stationCount: Int? = null,

                // 버스/지하철 노선(예시 JSON은 버스 세트)
                val lane: List<LaneDto>? = null,

                // 배차 간격(분)
                val intervalTime: Int? = null,

                // 구간 시작/끝 정보
                val startName: String? = null,
                val startX: Double? = null,
                val startY: Double? = null,
                val endName: String? = null,
                val endX: Double? = null,
                val endY: Double? = null,
                val startID: Long? = null,
                val startStationCityCode: Int? = null,
                val startStationProviderCode: Int? = null,
                val startLocalStationID: String? = null,
                val startArsID: String? = null,
                val endID: Long? = null,
                val endStationCityCode: Int? = null,
                val endStationProviderCode: Int? = null,
                val endLocalStationID: String? = null,
                val endArsID: String? = null,

                // 경유 정류장 목록
                val passStopList: PassStopListDto? = null
            ) {
                data class LaneDto(
                    val busNo: String? = null,
                    val type: Int? = null,
                    val busID: Long? = null,
                    val busLocalBlID: String? = null,
                    val busCityCode: Int? = null,
                    val busProviderCode: Int? = null
                    // 지하철 케이스가 추가로 필요하면 필드 확장
                )

                data class PassStopListDto(
                    val stations: List<StationDto>?
                ) {
                    data class StationDto(
                        val index: Int,
                        val stationID: Long,
                        val stationName: String,
                        val stationCityCode: Int,
                        val stationProviderCode: Int,
                        val localStationID: String,
                        val arsID: String,
                        val x: String,            // 경도 문자열
                        val y: String,            // 위도 문자열
                        val isNonStop: String
                    )
                }
            }
        }
    }
}
