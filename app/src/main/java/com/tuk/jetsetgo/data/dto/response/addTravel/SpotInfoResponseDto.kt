package com.tuk.jetsetgo.data.dto.response.addTravel

import com.tuk.jetsetgo.domain.model.response.addTravel.SpotInfoResponseModel

data class SpotInfoResponseDto(
    val touristSpotInfoList: List<TouristSpotInfoListDto>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class TouristSpotInfoListDto(
        val id: Int,
        val name: String,
        val tel: String?,
        val category: String?,
        val businessStatus: String?,
        val address: String?,
        val thumbnailUrl: String?,
        val thumbnailUrls: String?,
        val latitude: Double?,
        val longitude: Double?,
        val activityLevel: String?,
        val homePage: String?,
        val naverBookingUrl: String?,
        val travelCityId: Int?
    ) {
        fun toTouristSpotInfoListModel() =
            SpotInfoResponseModel.TouristSpotInfoListModel(
                id,
                name,
                tel,
                category,
                businessStatus,
                address,
                thumbnailUrl,
                thumbnailUrls,
                latitude,
                longitude,
                activityLevel,
                homePage,
                naverBookingUrl,
                travelCityId
            )
    }

    fun toSpotInfoResponseModel() =
        SpotInfoResponseModel(touristSpotInfoList.map { it.toTouristSpotInfoListModel() }, totalPages, totalElements, isFirst, isLast)
}
