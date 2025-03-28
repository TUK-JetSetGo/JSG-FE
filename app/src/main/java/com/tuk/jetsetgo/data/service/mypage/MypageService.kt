package com.tuk.jetsetgo.data.service.mypage

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.response.mypage.GetUserResponseDto
import retrofit2.http.GET

interface MypageService {
    @GET("/user/profile")
    suspend fun getUser(
    ): BaseResponse<GetUserResponseDto>

}