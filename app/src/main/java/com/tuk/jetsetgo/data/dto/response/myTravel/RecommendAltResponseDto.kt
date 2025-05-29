package com.tuk.jetsetgo.data.dto.response.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.RecommendAltResponseModel

data class RecommendAltResponseDto(
    val alternatives: Map<String, List<Int>>
) {
    fun toRecommendAltResponseModel() =
        RecommendAltResponseModel(
            alternatives = alternatives
        )
}