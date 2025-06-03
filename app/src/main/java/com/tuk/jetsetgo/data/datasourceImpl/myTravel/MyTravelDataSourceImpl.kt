package com.tuk.jetsetgo.data.datasourceImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.MyTravelDataSource
import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.myTravel.ExpenseRequestDto
import com.tuk.jetsetgo.data.dto.response.myTravel.GetCheckListResponseDto
import com.tuk.jetsetgo.data.dto.request.myTravel.PostCheckListRequestDto
import com.tuk.jetsetgo.data.dto.response.myTravel.ExpenseDateResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.ExpenseDetailResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.MyPlanResponseDto
import com.tuk.jetsetgo.data.dto.response.myTravel.PlanInfoResponseDto
import com.tuk.jetsetgo.data.service.myTravel.MyTravelService
import javax.inject.Inject

class MyTravelDataSourceImpl @Inject constructor(
    private val myTravelService: MyTravelService
) : MyTravelDataSource {
    override suspend fun fetchMyTravelList(): BaseResponse<MyPlanResponseDto> =
        myTravelService.fetchMyTravelList()

    override suspend fun fetchTravelPlan(
        travelPlanId: Int,
        dayIndex: Int,
    ): BaseResponse<PlanInfoResponseDto> =
        myTravelService.fetchTravelPlan(travelPlanId, dayIndex)

    override suspend fun fetchSaveExpense(request: ExpenseRequestDto): BaseResponse<String> =
        myTravelService.fetchSaveExpense(request)

    override suspend fun fetchExpenseDetail(expenseId: Int): BaseResponse<ExpenseDetailResponseDto> =
        myTravelService.fetchExpenseDetail(expenseId)

    override suspend fun fetchDeleteExpense(expenseId: Int): BaseResponse<String> =
        myTravelService.fetchDeleteExpense(expenseId)

    override suspend fun fetchEditExpense(request: ExpenseRequestDto): BaseResponse<String> =
        myTravelService.fetchEditExpense(request)

    override suspend fun fetchExpenseDate(
        itineraryId: Int,
        page: Int,
        size: Int,
        sort: String?,
    ): BaseResponse<ExpenseDateResponseDto> =
        myTravelService.fetchExpenseDate(itineraryId, page, size, sort)


    override suspend fun getCheckList(
        travelPlanId: Int
    ): BaseResponse<GetCheckListResponseDto> =
        myTravelService.getCheckList(travelPlanId)

    override suspend fun postCheckList(
        travelPlanId: Int,
        request: PostCheckListRequestDto
    ): BaseResponse<String> =
        myTravelService.postCheckList(travelPlanId, request)

    override suspend fun patchCheckList(
        checklistId: Int,
        isChecked: Boolean
    ): BaseResponse<String> =
        myTravelService.patchCheckList(checklistId, isChecked)

    override suspend fun deleteCheckList(
        checklistId: Int
    ): BaseResponse<String> =
        myTravelService.deleteCheckList(checklistId)
}