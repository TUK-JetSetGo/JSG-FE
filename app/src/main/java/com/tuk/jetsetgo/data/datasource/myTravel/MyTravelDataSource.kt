package com.tuk.jetsetgo.data.datasource.myTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.response.myTravel.MyPlanResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.PlanInfoResponseDto

interface MyTravelDataSource {
    suspend fun fetchMyTravelList(): BaseResponse<MyPlanResponseDto>
    suspend fun fetchTravelPlan(travelPlanId: Int, dayIndex: Int): BaseResponse<PlanInfoResponseDto>
}