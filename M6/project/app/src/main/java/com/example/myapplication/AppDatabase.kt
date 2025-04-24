package com.example.myapplication

import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.UserEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.dao.TweetDao
import com.example.myapplication.entity.TweetEntity

@Database(entities = [UserEntity::class, TweetEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun tweetDao(): TweetDao

    companion object {
        val DATABASE_NAME = "tugas_prak_m6"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}