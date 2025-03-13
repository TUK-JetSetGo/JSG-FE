package com.tuk.jetsetgo.data.datasourceImpl.login

import com.tuk.jetsetgo.data.datasource.login.LoginDataSource
import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.login.SignUpRequestDto
import com.tuk.jetsetgo.data.dto.response.login.RefreshResponseDto
import com.tuk.jetsetgo.data.dto.response.login.SignUpResponseDto
import com.tuk.jetsetgo.data.service.login.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService
): LoginDataSource {
    override suspend fun fetchSignUp(request: SignUpRequestDto): BaseResponse<SignUpResponseDto> =
        loginService.fetchSignUp(request)

    override suspend fun fetchRefreshToken(refreshToken: String): BaseResponse<RefreshResponseDto> =
        loginService.fetchRefreshToken(refreshToken)
}