package com.tuk.jetsetgo.domain.model.response.mypage

data class GetUserResponseModel(
    val createdAt: String,
    val email: String,
    val name: String,
    val updatedAt: String,
    val userId: Int
)