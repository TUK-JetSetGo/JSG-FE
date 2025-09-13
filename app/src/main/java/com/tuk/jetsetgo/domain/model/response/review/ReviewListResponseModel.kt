package com.tuk.jetsetgo.domain.model.response.review

data class ReviewListResponseModel(
    val items: List<Item>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class Item(
        val travelPlanId: Int,
        val reviewName: String,
        val overallReviewId: Int,
        val rating: Double,
        val content: String
    )
}