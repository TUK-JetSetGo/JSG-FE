package com.tuk.jetsetgo.domain.model.request

import com.tuk.jetsetgo.data.dto.request.addTravel.CreatePlanRequestDto

data class CreatePlanRequestModel(
    val isGroup: Boolean,
    val groupSize: Int,
    val travelCityId: Int,
    val dailyVisitCount: Int,
    val activityStartTime: String,
    val activityEndTime: String,
    val travelStartDate: String,
    val travelEndDate: String,
    val travelPurposeId: Int,
    val travelThemeId: Int,
    val budget: Int,
    val travelSpotIdList: List<Int>
){
    fun toCreatePlanRequestDto() =
        CreatePlanRequestDto(isGroup, groupSize, travelCityId, dailyVisitCount, activityStartTime, activityEndTime, travelStartDate, travelEndDate, travelPurposeId, travelThemeId, budget, travelSpotIdList)
}
