package com.tuk.jetsetgo.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuk.jetsetgo.databinding.ItemCommentBinding

class CommentAdapter(
    private val commentList: MutableList<CommentData>   // ← MutableList로
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentData) {
            binding.tvCommentName.text = comment.username
            binding.tvCommentContent.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int = commentList.size


    fun addComment(comment: CommentData) {
        commentList.add(comment)
        notifyItemInserted(commentList.size - 1)
    }
}


