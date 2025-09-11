package com.tuk.jetsetgo.data.dto.response.review

import com.tuk.jetsetgo.domain.model.response.review.ReviewListResponseModel
import com.tuk.jetsetgo.domain.model.response.review.ReviewListResponseModel.ReviewListItemModel

data class GetReviewListResponseDto(
    val overallReviewInfoList: List<OverallReviewInfoDto>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class OverallReviewInfoDto(
        val overallReviewId: Int,
        val rating: Double,     // 서버가 int여도 Double로 받으면 포맷하기 쉬워요
        val content: String
    ) {
        fun toModel() = ReviewListItemModel(
            overallReviewId = overallReviewId,
            rating = rating,
            content = content
        )
    }

    fun toModel() = ReviewListResponseModel(
        items = overallReviewInfoList.map { it.toModel() },
        totalPages = totalPages,
        totalElements = totalElements,
        isFirst = isFirst,
        isLast = isLast
    )
}
