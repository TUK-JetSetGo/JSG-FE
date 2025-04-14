package com.tuk.jetsetgo.data.service.myTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.myTravel.ExpenseRequestDto
import com.tuk.jetsetgo.data.dto.response.myTravel.ExpenseDetailResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.MyPlanResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.PlanInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    // 지출 저장 API
    @POST("expenses")
    suspend fun fetchSaveExpense(
        @Body request: ExpenseRequestDto
    ): BaseResponse<String>

    // 지출 상세 조회 API
    @GET("expenses/{expenseId}")
    suspend fun fetchExpenseDetail(
        @Path("expenseId") expenseId: Int
    ): BaseResponse<ExpenseDetailResponseDto>

    // 지출 삭제 API
    @DELETE("expenses/{expenseId}")
    suspend fun fetchDeleteExpense(
        @Path("expenseId") expenseId: Int
    ): BaseResponse<String>

    // 지출 수정 API
    @PATCH("expenses/{expenseId}")
    suspend fun fetchEditExpense(
        @Body request: ExpenseRequestDto
    ): BaseResponse<String>

    // 날짜 별 지출 조회 API
    @GET("expenses/itinerary/{itineraryId}")
    suspend fun fetchExpenseDate(
        @Path("itineraryId") itineraryId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String?
    ): BaseResponse<ExpenseDateResponseDto>
}