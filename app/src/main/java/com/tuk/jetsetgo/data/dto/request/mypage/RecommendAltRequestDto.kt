package com.tuk.jetsetgo.data.dto.request.mypage

data class RecommendAltRequestDto(
    val itinerary: List<Int>,
    val modify_idx: List<Int>,
    val radius: Int,
    val recommend_count: Int
)