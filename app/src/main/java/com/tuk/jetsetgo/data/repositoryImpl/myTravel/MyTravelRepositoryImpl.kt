package com.tuk.jetsetgo.data.repositoryImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.MyTravelDataSource
import com.tuk.jetsetgo.domain.model.request.myTravel.ExpenseRequestModel
import com.tuk.jetsetgo.domain.model.request.myTravel.PostCheckListRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDateResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDetailResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.GetCheckListResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel
import com.tuk.jetsetgo.domain.repository.myTravel.MyTravelRepository
import javax.inject.Inject

class MyTravelRepositoryImpl @Inject constructor(
    private val myTravelDataSource: MyTravelDataSource
): MyTravelRepository {
    override suspend fun fetchMyTravelList(): Result<MyPlanResponseModel> = runCatching {
        myTravelDataSource.fetchMyTravelList().data.toMyPlanResponseModel()
    }

    override suspend fun fetchTravelPlan(
        travelPlanId: Int,
        dayIndex: Int,
    ): Result<PlanInfoResponseModel> = runCatching {
        myTravelDataSource.fetchTravelPlan(travelPlanId, dayIndex).data.toPlanInfoResponseModel()
    }

    override suspend fun fetchSaveExpense(request: ExpenseRequestModel): Result<String> = runCatching {
        myTravelDataSource.fetchSaveExpense(request.toExpenseRequestDto()).data
    }

    override suspend fun fetchExpenseDetail(expenseId: Int): Result<ExpenseDetailResponseModel> = runCatching {
        myTravelDataSource.fetchExpenseDetail(expenseId).data.toExpenseDetailResponseModel()
    }

    override suspend fun fetchDeleteExpense(expenseId: Int): Result<String> = runCatching {
        myTravelDataSource.fetchDeleteExpense(expenseId).data
    }

    override suspend fun fetchEditExpense(request: ExpenseRequestModel): Result<String> = runCatching {
        myTravelDataSource.fetchEditExpense(request.toExpenseRequestDto()).data
    }

    override suspend fun fetchExpenseDate(
        itineraryId: Int,
        page: Int,
        size: Int,
        sort: String?,
    ): Result<ExpenseDateResponseModel> = runCatching {
        myTravelDataSource.fetchExpenseDate(itineraryId, page, size, sort).data.toExpenseListResponseModel()
    }


    override suspend fun getCheckList(
        travelPlanId: Int
    ): Result<GetCheckListResponseModel> = runCatching {
        myTravelDataSource.getCheckList(travelPlanId).data.toGetCheckListResponseModel()
    }

    override suspend fun postCheckList(
        travelPlanId: Int,
        request: PostCheckListRequestModel
    ): Result<String> = runCatching {
        myTravelDataSource.postCheckList(travelPlanId, request.toPostCheckListRequestDto()).data
    }

    override suspend fun patchCheckList(
        checklistId: Int,
        isChecked: Boolean
    ): Result<String> = runCatching {
        myTravelDataSource.patchCheckList(checklistId, isChecked).data
    }

    override suspend fun deleteCheckList(
        checklistId: Int
    ): Result<String> = runCatching {
        myTravelDataSource.deleteCheckList(checklistId).data
    }
}