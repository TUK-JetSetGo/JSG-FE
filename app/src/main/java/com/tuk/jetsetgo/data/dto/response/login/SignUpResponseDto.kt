package com.tuk.jetsetgo.data.dto.response.login

import com.tuk.jetsetgo.domain.model.response.login.SignUpResponseModel

data class SignUpResponseDto(
    val userId: Int,
    val name: String,
    val email: String,
    val token: String,
    val refreshToken: String,
    val newUser: Boolean
) {
    fun toSignUpResponseModel() =
        SignUpResponseModel(userId, name, email, token, refreshToken, newUser)
}
