package com.tuk.jetsetgo.data.dto.request.myTravel

data class ExpenseRequestDto(
    val title: String,
    val amount: Int,
    val payerId: Int,
    val paymentMethod: String,
    val participants: Map<String, Int>,
    val itineraryId: Int
)
