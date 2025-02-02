package com.tuk.jetsetgo.domain.model.response

data class SelectCityResponseModel(
    val travelCityInfoList: List<TravelCityInfoListModel>
) {
    data class TravelCityInfoListModel(
        val id: Int,
        val name: String
    )
}
