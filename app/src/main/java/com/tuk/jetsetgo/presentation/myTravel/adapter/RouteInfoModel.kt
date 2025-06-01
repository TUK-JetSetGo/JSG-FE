package com.tuk.jetsetgo.presentation.myTravel.adapter

data class RouteInfoModel(
    val routeId: Int?,
    val touristSpotId: Int,
    val visitStartTime: String,
    val visitEndTime: String,
    val orderIndex: Int
)
