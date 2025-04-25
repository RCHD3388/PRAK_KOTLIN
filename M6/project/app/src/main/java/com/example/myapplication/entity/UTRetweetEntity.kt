package com.example.myapplication.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "retweets")
data class UTRetweetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_retweet") val id_retweet:String,
    @ColumnInfo(name = "user_username") val user_username:String,
    @ColumnInfo(name = "tweet_id") var tweet_id:String,
    @ColumnInfo(name = "is_deleted") val deleted:Boolean,
    @ColumnInfo(name = "retweeted") val retweeted:Boolean,
){
    override fun toString(): String {
        return "$user_username - $tweet_id"
    }
}