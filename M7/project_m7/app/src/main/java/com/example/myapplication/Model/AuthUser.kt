package com.example.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "auth_user")
data class AuthUser(
    @PrimaryKey(autoGenerate = false)
    val auth_key: String,
    val username:String,
    val loginTimestamp:Long,
)