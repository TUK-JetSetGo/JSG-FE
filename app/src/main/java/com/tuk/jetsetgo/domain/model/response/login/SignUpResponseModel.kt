package com.tuk.jetsetgo.domain.model.response.login

data class SignUpResponseModel(
    val userId: Int,
    val name: String,
    val email: String,
    val token: String,
    val refreshToken: String,
    val newUser: Boolean
)
