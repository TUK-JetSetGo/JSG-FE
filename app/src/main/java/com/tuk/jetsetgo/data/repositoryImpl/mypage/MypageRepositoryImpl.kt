package com.tuk.jetsetgo.data.repositoryImpl.mypage

import com.tuk.jetsetgo.data.datasource.mypage.MypageDataSource
import com.tuk.jetsetgo.domain.model.response.mypage.GetUserResponseModel
import com.tuk.jetsetgo.domain.repository.mypage.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageDataSource: MypageDataSource
): MypageRepository {
    override suspend fun getUser(): Result<GetUserResponseModel> = runCatching {
        mypageDataSource.getUser().data.toGetUserResponseModel()
    }
}