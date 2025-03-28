package com.tuk.jetsetgo.data.dto.response.mypage

data class GetUserResponseDto(
    val createdAt: String,
    val email: String,
    val name: String,
    val updatedAt: String,
    val userId: Int
){
    fun toGetUserResponseDto() =
        GetUserResponseDto(createdAt, email, name, updatedAt, userId)
}