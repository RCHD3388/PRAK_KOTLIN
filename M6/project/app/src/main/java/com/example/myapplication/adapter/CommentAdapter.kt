package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.CommentItemBinding
import com.example.myapplication.entity.UTCommentEntity

class CommentDiffUtil: DiffUtil.ItemCallback<UTCommentEntity>(){
    override fun areItemsTheSame(oldItem: UTCommentEntity, newItem: UTCommentEntity): Boolean {
        return oldItem.id_comment == newItem.id_comment
    }

    override fun areContentsTheSame(oldItem: UTCommentEntity, newItem: UTCommentEntity): Boolean {
        return oldItem == newItem
    }
}

val commentDiffUtil = CommentDiffUtil()

class CommentAdapter: ListAdapter<UTCommentEntity, CommentAdapter.ViewHolder>(commentDiffUtil){

    class ViewHolder(val binding: CommentItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CommentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getItem(position)
        holder.binding.tvName.text = comment.user_name
        holder.binding.tvUsername.text = comment.user_username

        holder.binding.tvComment.text = comment.comment
    }
}