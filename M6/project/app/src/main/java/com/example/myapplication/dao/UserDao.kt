package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user:UserEntity)

    @Update
    suspend fun update(user:UserEntity)

    @Delete
    suspend fun delete(user:UserEntity)

    @Query("DELETE FROM users where username = :username")
    suspend fun deleteQuery(username: String):Int

    @Query("SELECT * FROM users")
    suspend fun fetch():List<UserEntity>

    @Query("SELECT * FROM users where username = :username")
    suspend fun get(username:String):UserEntity?
}