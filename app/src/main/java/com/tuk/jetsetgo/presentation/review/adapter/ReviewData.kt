package com.tuk.jetsetgo.presentation.review.adapter

data class ReviewData(
    val title: String,
    val nickname: String,
    val picture: List<String>,
    val like: String,
    val comment: String,
    val bookmark: String,
)
