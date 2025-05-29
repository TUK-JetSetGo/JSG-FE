package com.tuk.jetsetgo.domain.model.request.myTravel

import com.tuk.jetsetgo.data.dto.request.myTravel.RecommendAltRequestDto

data class RecommendAltRequestModel(
    val itinerary: List<Int>,
    val modifyIdx: List<Int>,
    val radius: Int,
    val recommendCount: Int
) {
    fun toRecommendAltRequestDto() =
        RecommendAltRequestDto(
            itinerary = itinerary,
            modify_idx = modifyIdx,
            radius = radius,
            recommend_count = recommendCount
        )
}