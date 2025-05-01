package com.tuk.jetsetgo.data.dto.request.addTravel

data class EditPlanRequestDto(
    val routes: List<RouteDto>
) {
    data class RouteDto(
        val routeId: Int?,
        val newTouristSpotId: Int?,
        val visitStartTime: String,
        val visitEndTime: String,
        val orderIndex: Int
    )
}
