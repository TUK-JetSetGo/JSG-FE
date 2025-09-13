package com.tuk.jetsetgo.domain.model.request.review

import com.tuk.jetsetgo.data.dto.request.review.*

data class PostReviewRequestModel(
    val overallRating: Double,
    val overallContent: String,
    val dailyList: List<DailyReviewItemModel>
) {
    fun toPostReviewRequestDto() =
        PostReviewRequestDto(
            overallReviewRequest = OverallReviewRequestDto(
                rating = overallRating,
                content = overallContent
            ),
            dailyReviewRequestList = dailyList.map {
                DailyReviewRequestDto(
                    dayIndex = it.dayIndex,
                    rating = it.rating,
                    content = it.content
                )
            }
        )
}

data class DailyReviewItemModel(
    val dayIndex: Int,
    val rating: Double,
    val content: String
)
