package com.example.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "chats")
@JsonClass(generateAdapter = true)
data class Chat(
    @PrimaryKey(autoGenerate = false)
    val chat_id:Int,
    val group_id:Int,
    var user_username:String,
    var user_name:String,
    var chat:String,
    var chat_time:Long,
)

@JsonClass(generateAdapter = true)
data class GroupChatResponse(
    val message:String,
    var data:List<Chat>?,
)

@JsonClass(generateAdapter = true)
data class SingleChatResponse(
    val message:String,
    var data:Chat?,
)

@JsonClass(generateAdapter = true)
data class CreateChatDro(
    val username: String,
    val chat: String,
    val chat_time: Long
)