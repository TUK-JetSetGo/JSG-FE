package com.tuk.jetsetgo.data.datasource.myTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.mypage.RecommendAltRequestDto
import com.tuk.jetsetgo.data.dto.response.myTravel.MyPlanResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.PlanInfoResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.RecommendAltResponseDto

interface MyTravelDataSource {
    suspend fun fetchMyTravelList(): BaseResponse<MyPlanResponseDto>
    suspend fun fetchTravelPlan(travelPlanId: Int, dayIndex: Int): BaseResponse<PlanInfoResponseDto>
    suspend fun postAlternativesRecommend(request: RecommendAltRequestDto): BaseResponse<RecommendAltResponseDto>
}