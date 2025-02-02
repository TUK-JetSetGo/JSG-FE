package com.tuk.jetsetgo.domain.model.response

data class PurposeResponseModel(
    val travelPurposeInfoList: List<TravelPurposeInfoListModel>
) {
    data class TravelPurposeInfoListModel(
        val id: Int,
        val name: String
    )
}
