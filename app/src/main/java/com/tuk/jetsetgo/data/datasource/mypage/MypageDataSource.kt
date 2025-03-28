package com.tuk.jetsetgo.data.datasource.mypage

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.response.mypage.GetUserResponseDto

interface MypageDataSource {
    suspend fun getUser(): BaseResponse<GetUserResponseDto>
}