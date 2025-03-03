package com.tuk.jetsetgo.data.dto.response.login

import com.tuk.jetsetgo.domain.model.response.login.RefreshResponseModel

data class RefreshResponseDto(
    val userId: Int,
    val name: String,
    val email: String,
    val token: String,
    val refreshToken: String,
    val newUser: Boolean
){
    fun toRefreshResponseModel() =
        RefreshResponseModel(userId, name, email, token, refreshToken, newUser)
}
