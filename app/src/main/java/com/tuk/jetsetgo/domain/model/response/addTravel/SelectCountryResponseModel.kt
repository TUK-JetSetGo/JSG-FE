package com.tuk.jetsetgo.domain.model.response.addTravel

data class SelectCountryResponseModel(
    val travelCountryInfoList: List<TravelCountryInfoListModel>
) {
    data class TravelCountryInfoListModel(
        val id: Int,
        val name: String
    )
}

