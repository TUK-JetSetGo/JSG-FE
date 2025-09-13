package com.tuk.jetsetgo.data.dto.response.review

import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel

data class GetReviewResponseDto(
    val travelPlanId: Int?, // nullable 로 변경
    val overallReviewInfo: OverallReviewInfo,
    val dailyReviewInfoList: List<DailyReviewInfo>
) {
    data class OverallReviewInfo(
        val travelPlanId: Int,
        val reviewName: String,
        val overallReviewId: Int,
        val rating: Double,
        val content: String
    ) {
        fun toModel() = GetReviewResponseModel.OverallReviewModel(
            travelPlanId = travelPlanId,
            reviewName = reviewName,
            overallReviewId = overallReviewId,
            rating = rating,
            content = content
        )
    }

    data class DailyReviewInfo(
        val itineraryInfo: ItineraryInfo,
        val dayIndex: Int,
        val reviewImageUrlList: List<String>,
        val rating: Double,
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
                    val touristSpotId: Long,   // Long 으로 변경
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
                    fun toModel() =
                        GetReviewResponseModel.DailyReviewModel.ItineraryModel.RouteModel.TouristSpotModel(
                            touristSpotId = touristSpotId,
                            name = name,
                            tel = tel,
                            category = category,
                            businessStatus = businessStatus,
                            address = address,
                            thumbnailUrl = thumbnailUrl,
                            thumbnailUrls = thumbnailUrls,
                            latitude = latitude,
                            longitude = longitude,
                            activityLevel = activityLevel,
                            homePage = homePage,
                            naverBookingUrl = naverBookingUrl,
                            travelCityId = travelCityId
                        )
                }

                fun toModel() =
                    GetReviewResponseModel.DailyReviewModel.ItineraryModel.RouteModel(
                        routeId = routeId,
                        orderIndex = orderIndex,
                        visitStartTime = visitStartTime,
                        visitEndTime = visitEndTime,
                        touristSpotInfo = touristSpotInfo.toModel()
                    )
            }

            fun toModel() =
                GetReviewResponseModel.DailyReviewModel.ItineraryModel(
                    itineraryId = itineraryId,
                    dayIndex = dayIndex,
                    routeInfoList = routeInfoList.map { it.toModel() }
                )
        }

        fun toModel() =
            GetReviewResponseModel.DailyReviewModel(
                itineraryInfo = itineraryInfo.toModel(),
                dayIndex = dayIndex,
                reviewImageUrlList = reviewImageUrlList,
                rating = rating,
                content = content
            )
    }

    fun toGetReviewResponseModel() = GetReviewResponseModel(
        travelPlanId = travelPlanId, // nullable 유지
        overallReviewInfo = overallReviewInfo.toModel(),
        dailyReviewInfoList = dailyReviewInfoList.map { it.toModel() }
    )
}
