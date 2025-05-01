package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.GroupFetchResponse
import com.example.myapplication.Model.Member
import com.example.myapplication.Model.User
import com.example.myapplication.databinding.MemberListItemBinding


class MemberDiffUtil:DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

val memberDiffUtil = MemberDiffUtil()

class MemberAdapter:ListAdapter<User, MemberAdapter.ViewHolder>(memberDiffUtil) {
    var onDeleteClickListener:((User)->Unit)? = null

    class ViewHolder(val binding: MemberListItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MemberListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.tvName.text = data.name

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClickListener?.invoke(data);
        }
    }
}