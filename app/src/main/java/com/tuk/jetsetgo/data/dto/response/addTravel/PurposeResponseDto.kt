package com.tuk.jetsetgo.data.dto.response.addTravel

import com.tuk.jetsetgo.domain.model.response.addTravel.PurposeResponseModel

data class PurposeResponseDto(
    val travelPurposeInfoList: List<TravelPurposeInfoListDto>
) {
    data class TravelPurposeInfoListDto(
        val id: Int,
        val name: String
    ) {
        fun toTravelPurposeInfoListModel() =
            PurposeResponseModel.TravelPurposeInfoListModel(id, name)
    }

    fun toPurposeResponseModel() =
        PurposeResponseModel(travelPurposeInfoList.map { it.toTravelPurposeInfoListModel() })
}
