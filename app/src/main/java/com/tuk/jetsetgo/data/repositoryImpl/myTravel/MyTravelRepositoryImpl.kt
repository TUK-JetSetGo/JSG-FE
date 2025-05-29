package com.tuk.jetsetgo.data.repositoryImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.MyTravelDataSource
import com.tuk.jetsetgo.domain.model.request.myTravel.RecommendAltRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.RecommendAltResponseModel
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

    override suspend fun postAlternativesRecommend(request: RecommendAltRequestModel): Result<RecommendAltResponseModel> = runCatching {
        myTravelDataSource.postAlternativesRecommend(request.toRecommendAltRequestDto()).data.toRecommendAltResponseModel()
    }
}