package com.tuk.jetsetgo.presentation.review.adapter

data class CreateReviewItemState(
    val day: Int,
    var rating: Float = 0f,
    var comment: String = ""
)