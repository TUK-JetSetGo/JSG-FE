package com.tuk.jetsetgo.data.dto.response.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel

data class MyPlanResponseDto(
    val myTravelPlanInfoList: List<MyTravelPlanInfoListDto>
){
    data class MyTravelPlanInfoListDto(
        val travelPlanId: Int,
        val travelStartDate: String,
        val travelEndDate: String,
        val travelDuration: String
    ) {
        fun toMyTravelPlanInfoListModel() =
            MyPlanResponseModel.MyTravelPlanInfoListModel(travelPlanId, travelStartDate, travelEndDate, travelDuration)
    }
    fun toMyPlanResponseModel() =
        MyPlanResponseModel(myTravelPlanInfoList.map { it.toMyTravelPlanInfoListModel() })
}
