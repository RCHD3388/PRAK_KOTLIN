package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.TweetEntity

@Dao
interface TweetDao {
    @Insert
    suspend fun insert(user:TweetEntity)

    @Update
    suspend fun update(user:TweetEntity)

    @Delete
    suspend fun delete(user:TweetEntity)

    @Query("DELETE FROM tweets where tweet_id = :tweetId")
    suspend fun deleteQuery(tweetId: Int):Int

    @Query("SELECT * FROM tweets")
    suspend fun fetch():List<TweetEntity>

    @Query("SELECT * FROM tweets where tweet_id = :tweetId")
    suspend fun get(tweetId:Int):TweetEntity?
}