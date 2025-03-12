package com.tuk.jetsetgo.domain.model.response.addTravel

data class SpotInfoResponseModel(
    val touristSpotInfoList: List<TouristSpotInfoListModel>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class TouristSpotInfoListModel(
        val id: Int,
        val name: String,
        val tel: String,
        val category: String,
        val businessStatus: String,
        val address: String,
        val thumbnailUrl: String,
        val thumbnailUrls: String,
        val latitude: Int,
        val longitude: Int,
        val activityLevel: String,
        val homePage: String,
        val naverBookingUrl: String,
        val travelCityId: Int
    )
}
