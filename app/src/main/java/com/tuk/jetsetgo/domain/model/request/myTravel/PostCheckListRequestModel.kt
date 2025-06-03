package com.tuk.jetsetgo.domain.model.request.myTravel

import com.tuk.jetsetgo.data.dto.request.myTravel.PostCheckListRequestDto

data class PostCheckListRequestModel(
    val itemName: String
){
    fun toPostCheckListRequestDto() =
        PostCheckListRequestDto(itemName)
}
