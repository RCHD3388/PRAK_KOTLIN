package com.example.myapplication.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "comments")
data class UTCommentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_comment") val id_comment:Long = 0,
    @ColumnInfo(name = "user_username") val user_username:String,
    @ColumnInfo(name = "user_name") var user_name:String,
    @ColumnInfo(name = "tweet_id") var tweet_id:Long,
    @ColumnInfo(name = "comment") val comment:String,
): Parcelable{
    override fun toString(): String {
        return "$user_username - $tweet_id"
    }
}