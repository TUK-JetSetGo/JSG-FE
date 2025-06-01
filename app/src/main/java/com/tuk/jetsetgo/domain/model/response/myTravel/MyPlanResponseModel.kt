package com.tuk.jetsetgo.domain.model.response.myTravel

data class MyPlanResponseModel(
    val myTravelPlanInfoList: List<MyTravelPlanInfoListModel>
) {
    data class MyTravelPlanInfoListModel(
        val travelPlanId: Int,
        val travelName: String,
        val travelStartDate: String,
        val travelEndDate: String,
        val travelDuration: String
    )
}
