package com.tuk.jetsetgo.data.dto.response.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel

data class PlanInfoResponseDto(
    val travelPlanId: Int,
    val travelStartDate: String,
    val travelEndDate: String,
    val itineraryInfoList: List<ItineraryInfoListDto>
) {
    data class ItineraryInfoListDto(
        val itineraryId: Int,
        val dayIndex: Int,
        val routeInfoList: List<RouteInfoListDto>
    ) {
        data class RouteInfoListDto(
            val routeId: Int,
            val orderIndex: Int,
            val visitStartTime: String,
            val visitEndTime: String,
            val touristSpotInfo: TouristSpotInfoDto
        ) {
            data class TouristSpotInfoDto(
                val touristSpotId: Int,
                val name: String,
                val tel: String,
                val category: String,
                val businessStatus: String,
                val address: String,
                val thumbnailUrl: String,
                val thumbnailUrls: String,
                val latitude: Double,
                val longitude: Double,
                val activityLevel: String,
                val homePage: String,
                val naverBookingUrl: String,
                val travelCityId: Int
            ) {
                fun toTouristSpotInfoModel() =
                    PlanInfoResponseModel.ItineraryInfoListModel.RouteInfoListModel.TouristSpotInfoModel(touristSpotId, name, tel, category, businessStatus, address, thumbnailUrl, thumbnailUrls, latitude, longitude, activityLevel, homePage, naverBookingUrl, travelCityId)
            }
            fun toRouteInfoListModel() =
                PlanInfoResponseModel.ItineraryInfoListModel.RouteInfoListModel(routeId, orderIndex, visitStartTime, visitEndTime, touristSpotInfo.toTouristSpotInfoModel())
        }
        fun toItineraryInfoListModel() =
            PlanInfoResponseModel.ItineraryInfoListModel(itineraryId, dayIndex, routeInfoList.map { it.toRouteInfoListModel() })
    }
    fun toPlanInfoResponseModel() =
        PlanInfoResponseModel(travelPlanId, travelStartDate, travelEndDate, itineraryInfoList.map { it.toItineraryInfoListModel() })
}
