package com.tuk.jetsetgo.data.dto.response.myTravel

import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDateResponseModel

data class ExpenseDateResponseDto(
    val expenseInfoList: List<ExpenseInfoDto>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class ExpenseInfoDto(
        val expenseId: Int,
        val title: String,
        val amount: Int,
        val paymentMethod: String,
        val payer: Payer,
        val expenseParticipantInfoList: List<ParticipantDto>
    ) {
        data class Payer(
            val userId: Int,
            val name: String,
            val amount: Int
        ){
            fun toPayerModel() =
                ExpenseDateResponseModel.ExpenseInfoModel.PayerModel(userId, name, amount)

        }
        data class ParticipantDto(
            val userId: Int,
            val name: String,
            val amount: Int
        ) {
            fun toParticipantModel() =
                ExpenseDateResponseModel.ExpenseInfoModel.ParticipantModel(userId, name, amount)
        }

        fun toExpenseInfoModel() = ExpenseDateResponseModel.ExpenseInfoModel(
            expenseId,
            title,
            amount,
            paymentMethod,
            payer.toPayerModel(),
            expenseParticipantInfoList.map { it.toParticipantModel() }
        )
    }

    fun toExpenseListResponseModel() =
        ExpenseDateResponseModel(expenseInfoList.map { it.toExpenseInfoModel() }, totalPages, totalElements, isFirst, isLast)
}
