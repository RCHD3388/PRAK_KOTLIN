package com.example.myapplication.entity.dto

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.myapplication.entity.TweetEntity

data class UserTweetDto (
    val tweetEntity: TweetEntity,
    val current_user_like_status: Boolean
){

}