package com.tuk.jetsetgo.data.datasourceImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.MyTravelDataSource
import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.mypage.RecommendAltRequestDto
import com.tuk.jetsetgo.data.dto.response.myTravel.MyPlanResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.PlanInfoResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.RecommendAltResponseDto
import com.tuk.jetsetgo.data.service.myTravel.MyTravelService
import javax.inject.Inject

class MyTravelDataSourceImpl @Inject constructor(
    private val myTravelService: MyTravelService
): MyTravelDataSource {
    override suspend fun fetchMyTravelList(): BaseResponse<MyPlanResponseDto> =
        myTravelService.fetchMyTravelList()

    override suspend fun fetchTravelPlan(
        travelPlanId: Int,
        dayIndex: Int,
    ): BaseResponse<PlanInfoResponseDto> =
        myTravelService.fetchTravelPlan(travelPlanId, dayIndex)

    override suspend fun postAlternativesRecommend(request: RecommendAltRequestDto): BaseResponse<RecommendAltResponseDto> =
        myTravelService.postAlternativesRecommend(request)
}