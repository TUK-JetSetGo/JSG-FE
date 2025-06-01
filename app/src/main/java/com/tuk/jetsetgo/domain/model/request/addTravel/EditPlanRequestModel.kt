package com.tuk.jetsetgo.domain.model.request.addTravel

import com.tuk.jetsetgo.data.dto.request.addTravel.EditPlanRequestDto

data class EditPlanRequestModel(
    val routes: List<RouteModel>
) {
    data class RouteModel(
        val routeId: Int?,
        val newTouristSpotId: Int?,
        val visitStartTime: String,
        val visitEndTime: String,
        val orderIndex: Int
    ){
        fun toRouteDto() =
            EditPlanRequestDto.RouteDto(routeId, newTouristSpotId, visitStartTime, visitEndTime, orderIndex)
    }
    fun toEditPlanRequestDto() =
        EditPlanRequestDto(routes.map { it.toRouteDto() })
}

