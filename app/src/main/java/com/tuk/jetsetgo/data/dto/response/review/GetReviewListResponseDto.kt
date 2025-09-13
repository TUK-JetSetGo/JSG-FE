package com.tuk.jetsetgo.data.dto.response.review

import com.tuk.jetsetgo.domain.model.response.review.ReviewListResponseModel

data class GetReviewListResponseDto(
    val overallReviewInfoList: List<OverallReviewInfoDto>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class OverallReviewInfoDto(
        val travelPlanId: Int,
        val reviewName: String,
        val overallReviewId: Int,
        val rating: Double,
        val content: String
    )

    fun toModel() = ReviewListResponseModel(
        items = overallReviewInfoList.map { it.toModel() },
        totalPages = totalPages,
        totalElements = totalElements,
        isFirst = isFirst,
        isLast = isLast
    )

    private fun OverallReviewInfoDto.toModel() =
        ReviewListResponseModel.Item(
            travelPlanId, reviewName, overallReviewId, rating, content
        )
}
