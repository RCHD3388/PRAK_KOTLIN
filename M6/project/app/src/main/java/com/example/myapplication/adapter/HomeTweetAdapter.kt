package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.TweetItemBinding
import com.example.myapplication.entity.TweetEntity

class TweetDiffUtil: DiffUtil.ItemCallback<TweetEntity>(){
    override fun areItemsTheSame(oldItem: TweetEntity, newItem: TweetEntity): Boolean {
        return oldItem.tweet_id == newItem.tweet_id
    }

    override fun areContentsTheSame(oldItem: TweetEntity, newItem: TweetEntity): Boolean {
        return oldItem == newItem
    }
}

val tweetDiffUtil = TweetDiffUtil()

class HomeTweetAdapter: ListAdapter<TweetEntity, HomeTweetAdapter.ViewHolder>(tweetDiffUtil){
    var onLikeClickListener:((TweetEntity)->Unit)? = null
    var onCommentClickListener:((TweetEntity)->Unit)? = null
    var onRetweetClickListener:((TweetEntity)->Unit)? = null

    class ViewHolder(val binding: TweetItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TweetItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.tvName.text = user.user_name
        holder.binding.tvUsername.text = user.user_username
        holder.binding.tvTweet.text = user.tweet
        holder.binding.tvLike.text = "${user.like}"
        holder.binding.tvComments.text = "${user.comment}"
        holder.binding.tvRetweet.text = "${user.retweet}"

        holder.binding.ivLike.setOnClickListener {
            onLikeClickListener?.invoke(user)
        }
        holder.binding.ivComments.setOnClickListener {
            onCommentClickListener?.invoke(user)
        }
        holder.binding.ivRetweets.setOnClickListener {
            onRetweetClickListener?.invoke(user)
        }
    }
}