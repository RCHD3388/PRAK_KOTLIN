package com.example.myapplication.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tweets")
data class TweetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tweet_id") val tweet_id: Long = 0,
    @ColumnInfo(name = "user_username") val user_username:String,
    @ColumnInfo(name = "user_name") val user_name:String,
    @ColumnInfo(name = "tweet") var tweet:String,
    @ColumnInfo(name = "like") val like:Int,
    @ColumnInfo(name = "comment") val comment:Int,
    @ColumnInfo(name = "retweet") val retweet:Int,
    @ColumnInfo(name = "retweeted_from") val retweeted_from:Long? = null,
):Parcelable{
    override fun toString(): String {
        return "$user_name - $user_username";
    }
}