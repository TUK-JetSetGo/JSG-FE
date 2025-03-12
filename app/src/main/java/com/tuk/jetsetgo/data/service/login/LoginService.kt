package com.tuk.jetsetgo.data.service.login

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.login.SignUpRequestDto
import com.tuk.jetsetgo.data.dto.response.login.RefreshResponseDto
import com.tuk.jetsetgo.data.dto.response.login.SignUpResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("auth/login")
    suspend fun fetchSignUp(
        @Body request: SignUpRequestDto
    ): BaseResponse<SignUpResponseDto>

    @POST("auth/refresh")
    suspend fun fetchRefreshToken(
        @Path("Refresh-Token") refreshToken: String
    ): BaseResponse<RefreshResponseDto>
}