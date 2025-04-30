package com.example.myapplication.local.dao

import androidx.room.*
import com.example.myapplication.Model.AuthUser

@Dao
interface AuthUserDao {

    @Query("SELECT * FROM auth_user where auth_key = :auth_key")
    suspend fun getCurrentAuth(auth_key: String): AuthUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: AuthUser)

    @Query("DELETE FROM auth_user")
    suspend fun clearSession()
}
