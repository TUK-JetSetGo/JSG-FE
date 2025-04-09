package com.tuk.jetsetgo.data.repositoryImpl.mypage

import com.tuk.jetsetgo.data.datasource.mypage.MypageDataSource
import com.tuk.jetsetgo.domain.model.request.login.SignUpRequestModel
import com.tuk.jetsetgo.domain.model.request.mypage.PatchUserRequestModel
import com.tuk.jetsetgo.domain.model.response.login.SignUpResponseModel
import com.tuk.jetsetgo.domain.model.response.mypage.GetUserResponseModel
import com.tuk.jetsetgo.domain.model.response.mypage.PatchUserResponseModel
import com.tuk.jetsetgo.domain.repository.mypage.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageDataSource: MypageDataSource
): MypageRepository {
    override suspend fun getUser(): Result<GetUserResponseModel> = runCatching {
        mypageDataSource.getUser().data.toGetUserResponseModel()
    }

    override suspend fun patchUser(request: PatchUserRequestModel): Result<PatchUserResponseModel> = runCatching {
        mypageDataSource.patchUser(request.toPatchUserRequestDto()).data.toPatchUserResponseModel()
    }
}