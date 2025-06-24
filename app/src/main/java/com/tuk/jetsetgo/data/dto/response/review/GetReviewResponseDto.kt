package com.tuk.jetsetgo.data.dto.response.review

import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel

data class GetReviewResponseDto(
    val travelPlanId: Int,
    val overallReviewInfo: OverallReviewInfo,
    val dailyReviewInfoList: List<DailyReviewInfo>
) {
    data class OverallReviewInfo(
        val overallReviewId: Int,
        val rating: Int,
        val content: String
    ) {
        fun toModel() = GetReviewResponseModel.OverallReviewModel(
            overallReviewId, rating, content
        )
    }

    data class DailyReviewInfo(
        val itineraryInfo: ItineraryInfo,
        val dayIndex: Int,
        val reviewImageUrlList: List<String>,
        val rating: Int,
        val content: String
    ) {
        data class ItineraryInfo(
            val itineraryId: Int,
            val dayIndex: Int,
            val routeInfoList: List<RouteInfo>
        ) {
            data class RouteInfo(
                val routeId: Int,
                val orderIndex: Int,
                val visitStartTime: String,
                val visitEndTime: String,
                val touristSpotInfo: TouristSpotInfo
            ) {
                data class TouristSpotInfo(
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
                    fun toModel() = GetReviewResponseModel.DailyReviewModel.ItineraryModel.RouteModel.TouristSpotModel(
                        touristSpotId, name, tel, category, businessStatus, address,
                        thumbnailUrl, thumbnailUrls, latitude, longitude, activityLevel,
                        homePage, naverBookingUrl, travelCityId
                    )
                }

                fun toModel() = GetReviewResponseModel.DailyReviewModel.ItineraryModel.RouteModel(
                    routeId, orderIndex, visitStartTime, visitEndTime, touristSpotInfo.toModel()
                )
            }

            fun toModel() = GetReviewResponseModel.DailyReviewModel.ItineraryModel(
                itineraryId, dayIndex, routeInfoList.map { it.toModel() }
            )
        }

        fun toModel() = GetReviewResponseModel.DailyReviewModel(
            itineraryInfo.toModel(), dayIndex, reviewImageUrlList, rating, content
        )
    }

    fun toGetReviewResponseModel() = GetReviewResponseModel(
        travelPlanId = travelPlanId,
        overallReviewInfo = overallReviewInfo.toModel(),
        dailyReviewInfoList = dailyReviewInfoList.map { it.toModel() }
    )
}
