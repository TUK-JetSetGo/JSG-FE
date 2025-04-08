package com.tuk.jetsetgo.domain.model.response.mypage

data class PatchUserResponseModel(
    val createdAt: String,
    val email: String,
    val name: String,
    val updatedAt: String,
    val userId: Int
)