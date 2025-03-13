package com.tuk.jetsetgo.data.dto.request.addTravel

data class CreatePlanRequestDto(
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
    val travelSpotIdList: List<Int>?
)
