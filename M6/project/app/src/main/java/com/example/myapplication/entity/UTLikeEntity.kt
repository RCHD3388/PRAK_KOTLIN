package com.example.myapplication.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likes")
data class UTLikeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_like") val id_like:Long = 0,
    @ColumnInfo(name = "user_username") val user_username:String,
    @ColumnInfo(name = "tweet_id") var tweet_id:Long,
    @ColumnInfo(name = "is_deleted") val deleted:Boolean,
    @ColumnInfo(name = "liked") val liked:Boolean,
){
    override fun toString(): String {
        return "$user_username - $tweet_id"
    }
}