package com.example.myapplication.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.Model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user:User)

    @Query("DELETE FROM users where username = :username")
    suspend fun deleteQuery(username: String):Int

    @Query("SELECT * FROM users")
    suspend fun fetch():List<User>

    @Query("SELECT * FROM users where username = :username")
    suspend fun get(username:String):User?
}