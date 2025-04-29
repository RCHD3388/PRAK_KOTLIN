package com.example.myapplication.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Model.User
import com.example.myapplication.local.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        val DATABASE_NAME = "222117056_tugas_prak_m7"
    }
}