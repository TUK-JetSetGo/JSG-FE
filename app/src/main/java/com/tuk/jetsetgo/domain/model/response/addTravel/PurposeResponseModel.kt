package com.tuk.jetsetgo.domain.model.response.addTravel

data class PurposeResponseModel(
    val travelPurposeInfoList: List<TravelPurposeInfoListModel>
) {
    data class TravelPurposeInfoListModel(
        val id: Int,
        val name: String
    )
}
