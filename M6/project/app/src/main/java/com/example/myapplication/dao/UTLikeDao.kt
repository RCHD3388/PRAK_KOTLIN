package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.UTLikeEntity

@Dao
interface UTLikeDao {
    @Insert
    suspend fun insert(entity: UTLikeEntity)

    @Update
    suspend fun update(user: UTLikeEntity)

    @Query("SELECT * FROM likes")
    suspend fun fetch():List<UTLikeEntity>

    @Query("SELECT * FROM likes where user_username = :username AND tweet_id = :tweetId")
    suspend fun get(username:String, tweetId: String): UTLikeEntity?
}