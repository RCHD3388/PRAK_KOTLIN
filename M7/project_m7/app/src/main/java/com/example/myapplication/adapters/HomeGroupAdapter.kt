package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.GroupFetchResponse
import com.example.myapplication.databinding.GroupItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GroupFetchResponseDiffUtil:DiffUtil.ItemCallback<GroupFetchResponse>(){
    override fun areItemsTheSame(oldItem: GroupFetchResponse, newItem: GroupFetchResponse): Boolean {
        return oldItem.group.id == newItem.group.id
    }

    override fun areContentsTheSame(oldItem: GroupFetchResponse, newItem: GroupFetchResponse): Boolean {
        return oldItem == newItem
    }
}

val groupFetchResponseDiffUtil = GroupFetchResponseDiffUtil()

class HomeGroupAdapter:ListAdapter<GroupFetchResponse, HomeGroupAdapter.ViewHolder>(groupFetchResponseDiffUtil) {
    var onItemClickListener:((GroupFetchResponse)->Unit)? = null

    class ViewHolder(val binding:GroupItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GroupItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupData = getItem(position)
        holder.binding.tvName.text = groupData.alternatif_name
        holder.binding.tvLatestchat.text = groupData.last_chat?.chat ?: "-"
        holder.binding.tvLatestChatDate.text = if(groupData.last_chat == null){ "---" }else{ formatDate(groupData.last_chat!!.chat_time) }

        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(groupData)
        }
    }

    companion object {
        fun formatDate(milis: Long): String {
            val today = Calendar.getInstance()
            val date = Date(milis)

            val calendar = Calendar.getInstance().apply { time = date }
            val isToday = today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    today.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)

            val fullDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            return if (isToday) {
                "Today, ${timeFormat.format(date)}"
            } else {
                fullDateFormat.format(date)
            }
        }
    }
}