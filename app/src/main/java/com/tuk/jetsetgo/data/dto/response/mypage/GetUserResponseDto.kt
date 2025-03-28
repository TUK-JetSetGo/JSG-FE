package com.tuk.jetsetgo.data.dto.response.mypage

import com.tuk.jetsetgo.domain.model.response.mypage.GetUserResponseModel

data class GetUserResponseDto(
    val createdAt: String,
    val email: String,
    val name: String,
    val updatedAt: String,
    val userId: Int
){
    fun toGetUserResponseModel() =
        GetUserResponseModel(createdAt, email, name, updatedAt, userId)
}