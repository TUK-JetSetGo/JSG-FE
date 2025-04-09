package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemSpendBinding

class SpendAdapter(
    private var spends: List<SpendData>,
    private val onSpendClick: () -> Unit
) : RecyclerView.Adapter<SpendAdapter.SpendViewHolder>() {

    inner class SpendViewHolder(private val binding: ItemSpendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spendData: SpendData) {
            binding.tvSpendTitle.text = spendData.title
            binding.tvSpendPrice.text = spendData.price
            binding.tvSpendPerson.text = spendData.spendPerson
            binding.tvSpendParticipants.text = spendData.participants
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendViewHolder {
        val binding = ItemSpendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpendViewHolder, position: Int) {
        holder.bind(spends[position])
    }

    override fun getItemCount(): Int = spends.size

    // 리스트 갱신용 함수
    fun updateList(newSpends: List<SpendData>) {
        this.spends = newSpends
        notifyDataSetChanged()
    }
}

