package com.tuk.jetsetgo.data.dto.response.mypage

import com.tuk.jetsetgo.domain.model.response.mypage.PatchUserResponseModel

data class PatchUserResponseDto(
    val createdAt: String,
    val email: String,
    val name: String,
    val updatedAt: String,
    val userId: Int
){
    fun toPatchUserResponseModel() =
        PatchUserResponseModel(createdAt, email, name, updatedAt, userId)
}