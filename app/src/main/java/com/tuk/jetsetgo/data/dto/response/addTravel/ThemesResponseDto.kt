package com.tuk.jetsetgo.data.dto.response.addTravel

import com.tuk.jetsetgo.domain.model.response.ThemesResponseModel

data class ThemesResponseDto(
    val travelThemeInfoList: List<TravelThemeInfoListDto>
){
    data class TravelThemeInfoListDto(
        val id: Int,
        val name: String
    ){
        fun toTravelThemeInfoListModel() =
            ThemesResponseModel.TravelThemeInfoListModel(id, name)
    }
    fun toThemesResponseModel() =
        ThemesResponseModel(travelThemeInfoList.map { it.toTravelThemeInfoListModel() })
}
