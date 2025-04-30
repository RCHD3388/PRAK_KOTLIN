package com.example.myapplication.Model

// data/model/User.kt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "users")
@JsonClass(generateAdapter = true)
data class User(
    @PrimaryKey(autoGenerate = false)
    val username:String,
    var password:String,
    val name:String,
)

@JsonClass(generateAdapter = true)
data class LogRegDro(
    val username:String,
    var password:String,
)

@JsonClass(generateAdapter = true)
data class LogRegResponse(
    val message:String,
    var data:User?,
)

