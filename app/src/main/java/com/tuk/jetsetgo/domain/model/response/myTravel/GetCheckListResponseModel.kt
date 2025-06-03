package com.tuk.jetsetgo.domain.model.response.myTravel

data class GetCheckListResponseModel(
    val checklistId: Int,
    val isChecked: Boolean,
    val itemName: String
)
