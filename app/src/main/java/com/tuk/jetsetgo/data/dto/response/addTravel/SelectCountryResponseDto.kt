package com.tuk.jetsetgo.data.dto.response.addTravel

import com.tuk.jetsetgo.domain.model.response.SelectCountryResponseModel

data class SelectCountryResponseDto(
    val travelCountryInfoList: List<TravelCountryInfoListDto>
) {
    data class TravelCountryInfoListDto(
        val id: Int,
        val name: String
    ) {
        fun toTravelCountryInfoListModel() =
            SelectCountryResponseModel.TravelCountryInfoListModel(id, name)
    }

    fun toSelectCountryResponseModel() =
        SelectCountryResponseModel(travelCountryInfoList.map { it.toTravelCountryInfoListModel() })
}
