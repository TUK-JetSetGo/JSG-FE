package com.tuk.jetsetgo.data.dto.response.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.GetCheckListResponseModel

data class GetCheckListResponseDto(
    val checklistId: Int,
    val isChecked: Boolean,
    val itemName: String
){
    fun toGetCheckListResponseModel() =
        GetCheckListResponseModel(checklistId, isChecked, itemName)
}
