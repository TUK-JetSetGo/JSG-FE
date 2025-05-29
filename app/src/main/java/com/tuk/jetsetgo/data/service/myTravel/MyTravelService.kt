package com.tuk.jetsetgo.data.service.myTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.myTravel.RecommendAltRequestDto
import com.tuk.jetsetgo.data.dto.response.myTravel.MyPlanResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.PlanInfoResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.RecommendAltResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MyTravelService {
    // 내 여행 일정 리스트 조회 API
    @GET("travel-plans/my")
    suspend fun fetchMyTravelList(): BaseResponse<MyPlanResponseDto>

    // 여행 일정 조회 API
    @GET("travel-plans/{travelPlanId}")
    suspend fun fetchTravelPlan(
        @Path("travelPlanId") travelPlanId: Int,
        @Query("dayIndex") dayIndex: Int
    ): BaseResponse<PlanInfoResponseDto>

    // 대체 여행지 추천
    @POST("api/v1/alternatives/recommend")
    suspend fun postAlternativesRecommend(
        @Body request: RecommendAltRequestDto
    ): BaseResponse<RecommendAltResponseDto>
}