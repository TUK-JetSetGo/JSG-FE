package com.tuk.jetsetgo.domain.model.response.myTravel

data class ExpenseDateResponseModel(
    val expenseInfoList: List<ExpenseInfoModel>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class ExpenseInfoModel(
        val expenseId: Int,
        val title: String,
        val amount: Int,
        val paymentMethod: String,
        val payer: PayerModel,
        val expenseParticipantInfoList: List<ParticipantModel>
    ) {
        data class PayerModel(
            val userId: Int,
            val name: String,
            val amount: Int
        )

        data class ParticipantModel(
            val userId: Int,
            val name: String,
            val amount: Int
        )
    }
}
