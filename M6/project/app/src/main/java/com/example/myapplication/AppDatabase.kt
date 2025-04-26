package com.example.myapplication

import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.UserEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.dao.NotificationDao
import com.example.myapplication.dao.TweetDao
import com.example.myapplication.dao.UTCommentDao
import com.example.myapplication.dao.UTLikeDao
import com.example.myapplication.dao.UTRetweetDao
import com.example.myapplication.entity.NotificationEntity
import com.example.myapplication.entity.TweetEntity
import com.example.myapplication.entity.UTCommentEntity
import com.example.myapplication.entity.UTLikeEntity
import com.example.myapplication.entity.UTRetweetEntity

@Database(entities = [UserEntity::class, TweetEntity::class, UTCommentEntity::class,
                     UTLikeEntity::class, UTRetweetEntity::class, NotificationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun tweetDao(): TweetDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): UTCommentDao
    abstract fun likeDao(): UTLikeDao
    abstract fun retweetDao(): UTRetweetDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        val DATABASE_NAME = "222117056_tugas_prak_m6"
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