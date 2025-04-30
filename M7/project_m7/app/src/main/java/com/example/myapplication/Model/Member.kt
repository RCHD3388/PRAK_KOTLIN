package com.example.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "members")
@JsonClass(generateAdapter = true)
data class Member(
    @PrimaryKey(autoGenerate = false)
    val member_id:Int,
    val group_id:Int,
    var user_username:String
)