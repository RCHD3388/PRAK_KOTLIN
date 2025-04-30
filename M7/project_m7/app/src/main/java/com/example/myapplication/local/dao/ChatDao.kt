package com.example.myapplication.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.Model.Chat
import com.example.myapplication.Model.Group

@Dao
interface ChatDao {
    @Insert()
    suspend fun insert(chat: Chat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChats(chats: List<Chat>)

    @Query("SELECT * FROM chats WHERE group_id = :groupId ORDER BY chat_time DESC LIMIT 1")
    suspend fun fetchLastChat(groupId: Int): Chat?

    @Query("SELECT * FROM chats WHERE group_id = :groupId")
    suspend fun fetchGroupsByIds(groupId: Int): List<Chat>
}