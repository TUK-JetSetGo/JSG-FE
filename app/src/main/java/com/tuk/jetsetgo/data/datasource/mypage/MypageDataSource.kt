package com.tuk.jetsetgo.data.datasource.mypage

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.login.SignUpRequestDto
import com.tuk.jetsetgo.data.dto.request.mypage.PatchUserRequestDto
import com.tuk.jetsetgo.data.dto.response.mypage.GetUserResponseDto
import com.tuk.jetsetgo.data.dto.response.mypage.PatchUserResponseDto

interface MypageDataSource {
    suspend fun getUser(): BaseResponse<GetUserResponseDto>
    suspend fun patchUser(request: PatchUserRequestDto): BaseResponse<PatchUserResponseDto>
}