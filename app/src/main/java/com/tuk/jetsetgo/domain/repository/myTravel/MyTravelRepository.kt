package com.tuk.jetsetgo.domain.repository.myTravel

import com.tuk.jetsetgo.domain.model.request.myTravel.RecommendAltRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.RecommendAltResponseModel

interface MyTravelRepository {
    suspend fun fetchMyTravelList(): Result<MyPlanResponseModel>
    suspend fun fetchTravelPlan(travelPlanId: Int, dayIndex: Int): Result<PlanInfoResponseModel>
    suspend fun postAlternativesRecommend(request: RecommendAltRequestModel): Result<RecommendAltResponseModel?>
}