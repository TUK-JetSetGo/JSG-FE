package com.tuk.jetsetgo.data.dto.response.myTravel

data class ExpenseDetailResponseDto(
    val expenseId: Int,
    val title: String,
    val amount: Int,
    val paymentMethod: String,
    val payer: PayerInfo,
    val expenseParticipantInfoList: List<ParticipantInfo>
) {
    data class PayerInfo(
        val userId: Int,
        val name: String,
        val amount: Int
    )

    data class ParticipantInfo(
        val userId: Int,
        val name: String,
        val amount: Int
    )
}
