package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Chat
import com.example.myapplication.Model.User
import com.example.myapplication.databinding.ChatListItemBinding
import com.example.myapplication.local.AppDatabase


class ChatDiffUtil:DiffUtil.ItemCallback<Chat>(){
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.chat_id == newItem.chat_id
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}

val chatDiffUtil = ChatDiffUtil()

class ChatAdapter:ListAdapter<Chat, ChatAdapter.ViewHolder>(chatDiffUtil) {
    var onNameClickListener:((Chat)->Unit)? = null
    var withName = true

    class ViewHolder(val binding: ChatListItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.tvChatSender.visibility = View.VISIBLE
        holder.binding.tvNameSender.visibility = View.VISIBLE
        holder.binding.tvChatNSender.visibility = View.VISIBLE
        holder.binding.tvNameNSender.visibility = View.VISIBLE

        if(data.user_username == (AppDatabase.currentUser?.username ?: "")){
            holder.binding.tvChatNSender.visibility = View.GONE
            holder.binding.tvNameNSender.visibility = View.GONE

            holder.binding.tvChatSender.text = data.chat
            holder.binding.tvNameSender.text = data.user_name
        }else{
            holder.binding.tvChatSender.visibility = View.GONE
            holder.binding.tvNameSender.visibility = View.GONE

            holder.binding.tvChatNSender.text = data.chat
            holder.binding.tvNameNSender.text = data.user_name
        }

        if(!withName){
            holder.binding.tvNameSender.visibility = View.GONE
            holder.binding.tvNameNSender.visibility = View.GONE
        }else{
            holder.binding.tvNameSender.setOnClickListener{
                onNameClickListener?.invoke(data)
            }
            holder.binding.tvNameNSender.setOnClickListener{
                onNameClickListener?.invoke(data)
            }
        }
    }
}