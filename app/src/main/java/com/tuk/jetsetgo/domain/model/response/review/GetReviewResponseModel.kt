package com.tuk.jetsetgo.domain.model.response.review

data class GetReviewResponseModel(
    val travelPlanId: Int?,
    val overallReviewInfo: OverallReviewModel,
    val dailyReviewInfoList: List<DailyReviewModel>
) {
    data class OverallReviewModel(
        val travelPlanId: Int,
        val reviewName: String,
        val overallReviewId: Int,
        val rating: Double,
        val content: String
    )

    data class DailyReviewModel(
        val itineraryInfo: ItineraryModel,
        val dayIndex: Int,
        val reviewImageUrlList: List<String>,
        val rating: Double,
        val content: String
    ) {
        data class ItineraryModel(
            val itineraryId: Int,
            val dayIndex: Int,
            val routeInfoList: List<RouteModel>
        ) {
            data class RouteModel(
                val routeId: Int,
                val orderIndex: Int,
                val visitStartTime: String,
                val visitEndTime: String,
                val touristSpotInfo: TouristSpotModel
            ) {
                data class TouristSpotModel(
                    val touristSpotId: Long, // Long 으로 변경
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
}
