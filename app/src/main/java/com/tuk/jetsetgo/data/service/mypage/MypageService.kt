package com.tuk.jetsetgo.data.service.mypage

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.login.SignUpRequestDto
import com.tuk.jetsetgo.data.dto.request.mypage.PatchUserRequestDto
import com.tuk.jetsetgo.data.dto.response.mypage.GetUserResponseDto
import com.tuk.jetsetgo.data.dto.response.mypage.PatchUserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MypageService {
    @GET("/user/profile")
    suspend fun getUser(
    ): BaseResponse<GetUserResponseDto>

    @PATCH("/user/profile")
    suspend fun patchUser(
        @Body request: PatchUserRequestDto
    ): BaseResponse<PatchUserResponseDto>
}