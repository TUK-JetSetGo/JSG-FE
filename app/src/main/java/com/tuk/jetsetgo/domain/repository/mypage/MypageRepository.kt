package com.tuk.jetsetgo.domain.repository.mypage

import com.tuk.jetsetgo.domain.model.request.mypage.PatchUserRequestModel
import com.tuk.jetsetgo.domain.model.response.mypage.GetUserResponseModel
import com.tuk.jetsetgo.domain.model.response.mypage.PatchUserResponseModel

interface MypageRepository {
    suspend fun getUser(): Result<GetUserResponseModel>
    suspend fun patchUser(request: PatchUserRequestModel): Result<PatchUserResponseModel>
}