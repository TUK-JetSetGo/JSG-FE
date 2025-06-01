package com.tuk.jetsetgo.domain.model.request.myTravel

import com.tuk.jetsetgo.data.dto.request.myTravel.ExpenseRequestDto

data class ExpenseRequestModel(
    val title: String,
    val amount: Int,
    val payerId: Int,
    val paymentMethod: String,
    val participants: Map<String, Int>,
    val itineraryId: Int
) {
    fun toExpenseRequestDto() =
        ExpenseRequestDto(title, amount, payerId, paymentMethod, participants, itineraryId)
}
