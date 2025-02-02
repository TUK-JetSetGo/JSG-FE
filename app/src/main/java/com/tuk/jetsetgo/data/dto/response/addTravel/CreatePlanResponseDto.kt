package com.tuk.jetsetgo.data.dto.response.addTravel

import com.tuk.jetsetgo.domain.model.response.CreatePlanResponseModel

data class CreatePlanResponseDto(
    val data: String
){
    fun toCreatePlanResponseModel() =
        CreatePlanResponseModel(data)
}