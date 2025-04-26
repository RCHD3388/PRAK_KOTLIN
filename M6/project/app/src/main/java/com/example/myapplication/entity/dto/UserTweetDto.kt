package com.example.myapplication.entity.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.myapplication.entity.TweetEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserTweetDto (
    val tweetEntity: TweetEntity,
    val originalEntity: TweetEntity? = null,
    val current_user_like_status: Boolean
): Parcelable{

}