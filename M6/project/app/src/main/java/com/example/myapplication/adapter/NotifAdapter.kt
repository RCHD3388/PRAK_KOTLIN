package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.CommentItemBinding
import com.example.myapplication.databinding.NotifItemBinding
import com.example.myapplication.entity.NotificationEntity

class NotifDiffUtil: DiffUtil.ItemCallback<NotificationEntity>(){
    override fun areItemsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity): Boolean {
        return oldItem.notif_id == newItem.notif_id
    }

    override fun areContentsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity): Boolean {
        return oldItem == newItem
    }
}

val notifDiffUtil = NotifDiffUtil()

class NotifAdapter: ListAdapter<NotificationEntity, NotifAdapter.ViewHolder>(notifDiffUtil){

    class ViewHolder(val binding: NotifItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotifItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notif = getItem(position)
        holder.binding.tvNote.text = notif.note
    }
}