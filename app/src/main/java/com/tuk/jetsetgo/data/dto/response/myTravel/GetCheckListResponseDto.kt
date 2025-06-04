package com.tuk.jetsetgo.data.dto.response.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.GetCheckListResponseModel

data class GetCheckListResponseDto(
    val checklistId: Int,
    val isChecked: Boolean,
    val itemName: String
)
fun GetCheckListResponseDto.toModel() = GetCheckListResponseModel(
    checklistId = this.checklistId,
    isChecked = this.isChecked,
    itemName = this.itemName
)
