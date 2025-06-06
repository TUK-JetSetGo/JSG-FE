package com.tuk.jetsetgo.data.datasource.myTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.myTravel.ExpenseRequestDto
import com.tuk.jetsetgo.data.dto.response.myTravel.ExpenseDateResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.ExpenseDetailResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.MyPlanResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.PlanInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

interface MyTravelDataSource {
    suspend fun fetchMyTravelList(): BaseResponse<MyPlanResponseDto>
    suspend fun fetchTravelPlan(travelPlanId: Int, dayIndex: Int): BaseResponse<PlanInfoResponseDto>
    suspend fun fetchSaveExpense(request: ExpenseRequestDto): BaseResponse<String>
    suspend fun fetchExpenseDetail(expenseId: Int): BaseResponse<ExpenseDetailResponseDto>
    suspend fun fetchDeleteExpense(expenseId: Int): BaseResponse<String>
    suspend fun fetchEditExpense(request: ExpenseRequestDto): BaseResponse<String>
    suspend fun fetchExpenseDate(itineraryId: Int, page: Int?, size: Int?, sort: String?): BaseResponse<ExpenseDateResponseDto>
}