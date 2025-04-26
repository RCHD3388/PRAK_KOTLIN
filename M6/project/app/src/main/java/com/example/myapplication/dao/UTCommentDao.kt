package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.UTCommentEntity

@Dao
interface UTCommentDao {
    @Insert
    suspend fun insert(entity: UTCommentEntity)

    @Query("SELECT * FROM comments")
    suspend fun fetch():List<UTCommentEntity>

    @Query("SELECT * FROM comments where tweet_id = :tweetId")
    suspend fun fetchByTweet(tweetId:Long):List<UTCommentEntity>
}