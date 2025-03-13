package com.tuk.jetsetgo.domain.repository.login

import com.tuk.jetsetgo.domain.model.request.login.SignUpRequestModel
import com.tuk.jetsetgo.domain.model.response.login.RefreshResponseModel
import com.tuk.jetsetgo.domain.model.response.login.SignUpResponseModel


interface LoginRepository {
    suspend fun fetchSignUp(request: SignUpRequestModel): Result<SignUpResponseModel>
    suspend fun fetchRefreshToken(refreshToken: String): Result<RefreshResponseModel>
}