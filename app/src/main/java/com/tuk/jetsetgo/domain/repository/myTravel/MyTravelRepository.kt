package com.tuk.jetsetgo.domain.repository.myTravel

import com.tuk.jetsetgo.domain.model.request.myTravel.ExpenseRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDateResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDetailResponseModel
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
}