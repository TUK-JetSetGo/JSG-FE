package com.tuk.jetsetgo.domain.model.request.login

import com.tuk.jetsetgo.data.dto.request.login.SignUpRequestDto

data class SignUpRequestModel(
    val accessToken: String,
    val socialType: String
) {
    fun toSignUpRequestDto() =
        SignUpRequestDto(accessToken, socialType)
}