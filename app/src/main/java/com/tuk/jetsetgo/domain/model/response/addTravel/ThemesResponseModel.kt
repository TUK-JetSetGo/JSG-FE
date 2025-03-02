package com.tuk.jetsetgo.domain.model.response.addTravel

data class ThemesResponseModel(
    val travelThemeInfoList: List<TravelThemeInfoListModel>
) {
    data class TravelThemeInfoListModel(
        val id: Int,
        val name: String
    )
}
