package com.tuk.jetsetgo.data.datasourceImpl.mypage

import com.tuk.jetsetgo.data.datasource.mypage.MypageDataSource
import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.response.mypage.GetUserResponseDto
import com.tuk.jetsetgo.data.service.mypage.MypageService
import javax.inject.Inject

class MypageDataSourceImpl @Inject constructor(
    private val mypageService: MypageService
): MypageDataSource {
    override suspend fun getUser(): BaseResponse<GetUserResponseDto> =
        mypageService.getUser()

}