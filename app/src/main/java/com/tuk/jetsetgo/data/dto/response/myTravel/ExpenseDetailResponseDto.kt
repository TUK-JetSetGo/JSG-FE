package com.tuk.jetsetgo.data.dto.response.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDetailResponseModel

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
    ){
        fun toPayerModel() =
            ExpenseDetailResponseModel.PayerModel(userId, name, amount)
    }

    data class ParticipantInfo(
        val userId: Int,
        val name: String,
        val amount: Int
    ){
        fun toParticipantModel() =
            ExpenseDetailResponseModel.ParticipantModel(userId, name, amount)
    }
    fun toExpenseDetailResponseModel() =
        ExpenseDetailResponseModel(expenseId, title, amount, paymentMethod, payer.toPayerModel(), expenseParticipantInfoList.map { it.toParticipantModel() }
    )
}
