package com.tuk.jetsetgo.domain.model.response.myTravel

data class PlanInfoResponseModel(
    val travelPlanId: Int,
    val travelStartDate: String,
    val travelEndDate: String,
    val itineraryInfo: ItineraryInfoModel?
) {
    data class ItineraryInfoModel(
        val itineraryId: Int,
        val dayIndex: Int,
        val routeInfoList: List<RouteInfoListModel>
    ) {
        data class RouteInfoListModel(
            val routeId: Int,
            val orderIndex: Int,
            val visitStartTime: String,
            val visitEndTime: String,
            val touristSpotInfo: TouristSpotInfoModel
        ) {
            data class TouristSpotInfoModel(
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
            )
        }
    }
}
