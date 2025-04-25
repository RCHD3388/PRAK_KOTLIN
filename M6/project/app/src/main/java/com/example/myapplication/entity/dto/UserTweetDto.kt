package com.example.myapplication.entity.dto

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class UserTweetDto (
    val tweet_id: Long = 0,
    val user_username:String,
    val user_name:String,
    var tweet:String,
    val like:Int,
    val comment:Int,
    val retweet:Int,
    val is_retweeted:Boolean = false,
){

}