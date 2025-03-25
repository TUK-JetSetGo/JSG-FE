package com.tuk.jetsetgo.domain.repository.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel

interface MyTravelRepository {
    suspend fun fetchMyTravelList(): Result<MyPlanResponseModel>
    suspend fun fetchTravelPlan(travelPlanId: Int, dayIndex: Int): Result<PlanInfoResponseModel>
}