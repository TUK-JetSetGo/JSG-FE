package com.tuk.jetsetgo.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemCreateReviewBinding

class CreateReviewAdapter(
    private val items: MutableList<CreateReviewItemState>
) : RecyclerView.Adapter<CreateReviewAdapter.VH>() {

    inner class VH(val binding: ItemCreateReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CreateReviewItemState) = with(binding) {
            // "n일차" 라벨
            tvCreateReviewDay.text = "${item.day}일차"

            // RatingBar 바인딩 - 리스너 중복 방지 후 재설정
            createReviewRatingBar.setOnRatingBarChangeListener(null)
            createReviewRatingBar.rating = item.rating
            createReviewRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    items[pos].rating = rating
                }
            }

            // EditText 바인딩
            if (etCreateReviewTitle.text?.toString() != item.comment) {
                etCreateReviewTitle.setText(item.comment)
                etCreateReviewTitle.setSelection(etCreateReviewTitle.text?.length ?: 0)
            }
            etCreateReviewTitle.doAfterTextChanged { text ->
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    items[pos].comment = text?.toString().orEmpty()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCreateReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /** 외부에서 현재 입력 상태를 가져갈 때 사용 */
    fun getStates(): List<CreateReviewItemState> = items.toList()
}
