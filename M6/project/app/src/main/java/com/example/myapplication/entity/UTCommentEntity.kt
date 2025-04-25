package com.example.myapplication.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class UTCommentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_comment") val id_comment:String,
    @ColumnInfo(name = "user_username") val user_username:String,
    @ColumnInfo(name = "tweet_id") var tweet_id:String,
    @ColumnInfo(name = "comment") val comment:String,
){
    override fun toString(): String {
        return "$user_username - $tweet_id"
    }
}