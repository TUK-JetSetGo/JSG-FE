package com.tuk.jetsetgo.data.datasource.login

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.login.SignUpRequestDto
import com.tuk.jetsetgo.data.dto.response.login.RefreshResponseDto
import com.tuk.jetsetgo.data.dto.response.login.SignUpResponseDto

interface LoginDataSource {
    suspend fun fetchSignUp(request: SignUpRequestDto): BaseResponse<SignUpResponseDto>
    suspend fun fetchRefreshToken(refreshToken: String): BaseResponse<RefreshResponseDto>
}