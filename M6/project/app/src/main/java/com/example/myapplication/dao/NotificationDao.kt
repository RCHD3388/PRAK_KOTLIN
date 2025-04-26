package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.NotificationEntity
import com.example.myapplication.entity.TweetEntity

@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(user:NotificationEntity)

    @Query("SELECT * FROM notifs")
    suspend fun fetch():List<NotificationEntity>

    @Query("SELECT * FROM notifs where to_username = :username")
    suspend fun fetchByUsernameReceiver(username: String):List<NotificationEntity>
}