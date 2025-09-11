package com.tuk.jetsetgo.data.dto.request.review

data class PostReviewRequestDto(
    val overallReviewRequest: OverallReviewRequestDto,
    val dailyReviewRequestList: List<DailyReviewRequestDto>
)

data class OverallReviewRequestDto(
    val rating: Double,
    val content: String
)

data class DailyReviewRequestDto(
    val dayIndex: Int,
    val rating: Double,
    val content: String
)
