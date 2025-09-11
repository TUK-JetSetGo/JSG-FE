package com.tuk.jetsetgo.domain.model.response.review

data class ReviewListResponseModel(
    val items: List<ReviewListItemModel>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class ReviewListItemModel(
        val overallReviewId: Int,
        val rating: Double,
        val content: String
    )
}
