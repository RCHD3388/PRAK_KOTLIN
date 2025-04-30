package com.example.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_user")
data class AuthUser(
    @PrimaryKey(autoGenerate = false)
    val key: String,
    val username:String,
    val loginTimestamp:Long,
)