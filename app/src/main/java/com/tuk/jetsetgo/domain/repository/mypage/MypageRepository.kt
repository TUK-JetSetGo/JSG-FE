package com.tuk.jetsetgo.domain.repository.mypage

import com.tuk.jetsetgo.domain.model.response.mypage.GetUserResponseModel

interface MypageRepository {
    suspend fun getUser(): Result<GetUserResponseModel>
}