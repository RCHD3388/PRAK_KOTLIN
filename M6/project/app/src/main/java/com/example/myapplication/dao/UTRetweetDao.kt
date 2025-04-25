package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.UTLikeEntity
import com.example.myapplication.entity.UTRetweetEntity

@Dao
interface UTRetweetDao {
    @Insert
    suspend fun insert(entity: UTRetweetEntity)

    @Update
    suspend fun update(user: UTRetweetEntity)

    @Query("SELECT * FROM retweets")
    suspend fun fetch():List<UTRetweetEntity>

    @Query("SELECT * FROM retweets where user_username = :username AND tweet_id = :tweetId")
    suspend fun get(username:String, tweetId: String): UTRetweetEntity?

    @Query("SELECT * FROM retweets where tweet_id = :tweetId")
    suspend fun getTweetRetweets(tweetId: Long): List<UTRetweetEntity>?
}