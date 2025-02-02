package com.tuk.jetsetgo.data.dto.response.addTravel

import com.tuk.jetsetgo.domain.model.response.SelectCityResponseModel

data class SelectCityResponseDto(
    val travelCityInfoList: List<TravelCityInfoListDto>
) {
    data class TravelCityInfoListDto(
        val id: Int,
        val name: String
    ) {
        fun toTravelCityInfoListModel() =
            SelectCityResponseModel.TravelCityInfoListModel(id, name)
    }

    fun toSelectCityResponseModel() =
        SelectCityResponseModel(travelCityInfoList.map { it.toTravelCityInfoListModel() })
}
