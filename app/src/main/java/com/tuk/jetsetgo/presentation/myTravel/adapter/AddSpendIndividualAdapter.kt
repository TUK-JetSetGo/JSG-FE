package com.tuk.jetsetgo.presentation.myTravel.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemAddSpendPriceBinding

class AddSpendIndividualAdapter(
    private val items: List<String>,
    private val onAmountChanged: (Int) -> Unit // 콜백 추가
) : RecyclerView.Adapter<AddSpendIndividualAdapter.IndividualViewHolder>() {

    private val priceList = MutableList(items.size) { 0 }

    inner class IndividualViewHolder(
        private val binding: ItemAddSpendPriceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, position: Int) {
            binding.tvAddSpendParticipant.text = name
            binding.etAddSpendTotalPrice.setText("0")

            // 리스너 제거 후 재등록 (EditText 재활용 방지)
            binding.etAddSpendTotalPrice.setOnFocusChangeListener(null)
            binding.etAddSpendTotalPrice.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val amount = s?.toString()?.toIntOrNull() ?: 0
                    priceList[position] = amount
                    val total = priceList.sum()
                    onAmountChanged(total) // 합계 갱신
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndividualViewHolder {
        val binding = ItemAddSpendPriceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IndividualViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndividualViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}
