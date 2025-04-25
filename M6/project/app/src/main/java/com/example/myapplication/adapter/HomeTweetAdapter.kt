package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.TweetItemBinding
import com.example.myapplication.entity.dto.UserTweetDto

class TweetDiffUtil: DiffUtil.ItemCallback<UserTweetDto>(){
    override fun areItemsTheSame(oldItem: UserTweetDto, newItem: UserTweetDto): Boolean {
        return oldItem.tweetEntity.tweet_id == newItem.tweetEntity.tweet_id
    }

    override fun areContentsTheSame(oldItem: UserTweetDto, newItem: UserTweetDto): Boolean {
        return oldItem == newItem
    }
}

val tweetDiffUtil = TweetDiffUtil()

class HomeTweetAdapter: ListAdapter<UserTweetDto, HomeTweetAdapter.ViewHolder>(tweetDiffUtil){
    var onLikeClickListener:((UserTweetDto)->Unit)? = null
    var onCommentClickListener:((UserTweetDto)->Unit)? = null
    var onRetweetClickListener:((UserTweetDto)->Unit)? = null

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
        val tweet = getItem(position)
        holder.binding.tvName.text = tweet.tweetEntity.user_name
        holder.binding.tvUsername.text = tweet.tweetEntity.user_username
        holder.binding.tvTweet.text = tweet.tweetEntity.tweet
        holder.binding.tvLike.text = "${tweet.tweetEntity.like}"
        holder.binding.tvComments.text = "${tweet.tweetEntity.comment}"
        holder.binding.tvRetweet.text = "${tweet.tweetEntity.retweet}"
        holder.binding.ivLike.setImageResource(
            if(tweet.current_user_like_status){
                R.drawable.heart
            }else{
                R.drawable.heart_empty
            }
        )

        if(tweet.tweetEntity.retweeted_from != null){
            holder.binding.ivRetweetBadge.visibility = View.VISIBLE
            holder.binding.tvRetweetBadge.visibility = View.VISIBLE
        }else{
            holder.binding.ivRetweetBadge.visibility = View.GONE
            holder.binding.tvRetweetBadge.visibility = View.GONE
        }

        holder.binding.ivLike.setOnClickListener {
            onLikeClickListener?.invoke(tweet)
        }
        holder.binding.ivComments.setOnClickListener {
            onCommentClickListener?.invoke(tweet)
        }
        holder.binding.ivRetweets.setOnClickListener {
            onRetweetClickListener?.invoke(tweet)
        }
    }
}