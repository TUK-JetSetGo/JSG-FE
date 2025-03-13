package com.tuk.jetsetgo.data.repositoryImpl.login

import com.tuk.jetsetgo.data.datasource.login.LoginDataSource
import com.tuk.jetsetgo.domain.model.request.login.SignUpRequestModel
import com.tuk.jetsetgo.domain.model.response.login.RefreshResponseModel
import com.tuk.jetsetgo.domain.model.response.login.SignUpResponseModel
import com.tuk.jetsetgo.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
): LoginRepository {
    override suspend fun fetchSignUp(request: SignUpRequestModel): Result<SignUpResponseModel> = runCatching {
        loginDataSource.fetchSignUp(request.toSignUpRequestDto()).data.toSignUpResponseModel()
    }

    override suspend fun fetchRefreshToken(refreshToken: String): Result<RefreshResponseModel> = runCatching {
        loginDataSource.fetchRefreshToken(refreshToken).data.toRefreshResponseModel()
    }

}