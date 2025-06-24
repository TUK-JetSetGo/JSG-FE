package com.tuk.jetsetgo.domain.repository.myTravel

import com.tuk.jetsetgo.domain.model.request.myTravel.ExpenseRequestModel
import com.tuk.jetsetgo.domain.model.request.myTravel.PostCheckListRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDateResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDetailResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.GetCheckListResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel

interface MyTravelRepository {
    suspend fun fetchMyTravelList(): Result<MyPlanResponseModel>
    suspend fun fetchTravelPlan(travelPlanId: Int, dayIndex: Int): Result<PlanInfoResponseModel>
    suspend fun fetchSaveExpense(request: ExpenseRequestModel): Result<String>
    suspend fun fetchExpenseDetail(expenseId: Int): Result<ExpenseDetailResponseModel>
    suspend fun fetchDeleteExpense(expenseId: Int): Result<String>
    suspend fun fetchEditExpense(request: ExpenseRequestModel): Result<String>
    suspend fun fetchExpenseDate(itineraryId: Int?, page: Int?, size: Int?, sort: String?): Result<ExpenseDateResponseModel>

    suspend fun getCheckList(travelPlanId: Int): Result<List<GetCheckListResponseModel>>
    suspend fun postCheckList(travelPlanId: Int, request: PostCheckListRequestModel): Result<String>
    suspend fun patchCheckList(checklistId: Int, isChecked: Boolean): Result<String>
    suspend fun deleteCheckList(checklistId: Int): Result<String>

}