package com.tuk.jetsetgo.presentation.myTravel.adapter

data class ScheduleData(
    val routeId: Int?,               // 🔥 수정 시 필요한 ID
    val touristSpotId: Int,
    val title: String,
    val totalTime: String,
    val startTime: String,
    val endTime: String,
    val orderIndex: Int,
    val latitude: Double?,
    val longitude: Double?
)
