package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemSpendBinding
import com.tuk.jetsetgo.domain.model.response.myTravel.ExpenseDateResponseModel

class SpendAdapter(
    private val onSpendClick: (ExpenseDateResponseModel.ExpenseInfoModel) -> Unit
) : ListAdapter<ExpenseDateResponseModel.ExpenseInfoModel, SpendAdapter.SpendViewHolder>(SpendDiffCallback()) {

    inner class SpendViewHolder(private val binding: ItemSpendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExpenseDateResponseModel.ExpenseInfoModel) = with(binding) {
            tvSpendTitle.text = item.title
            tvSpendPrice.text = "${item.amount}원"
            tvSpendPerson.text = item.payer.name
            tvSpendParticipants.text =
                item.expenseParticipantInfoList.joinToString(", ") { it.name }

            root.setOnClickListener {
                onSpendClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendViewHolder {
        val binding = ItemSpendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpendViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class SpendDiffCallback : DiffUtil.ItemCallback<ExpenseDateResponseModel.ExpenseInfoModel>() {
        override fun areItemsTheSame(oldItem: ExpenseDateResponseModel.ExpenseInfoModel, newItem: ExpenseDateResponseModel.ExpenseInfoModel): Boolean {
            return oldItem.expenseId == newItem.expenseId
        }

        override fun areContentsTheSame(oldItem: ExpenseDateResponseModel.ExpenseInfoModel, newItem: ExpenseDateResponseModel.ExpenseInfoModel): Boolean {
            return oldItem == newItem
        }
    }
}

