package com.example.myapplication.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Model.AuthUser
import com.example.myapplication.Model.Chat
import com.example.myapplication.Model.Group
import com.example.myapplication.Model.Member
import com.example.myapplication.Model.User
import com.example.myapplication.local.dao.AuthUserDao
import com.example.myapplication.local.dao.ChatDao
import com.example.myapplication.local.dao.GroupDao
import com.example.myapplication.local.dao.MemberDao
import com.example.myapplication.local.dao.UserDao

@Database(entities = [
    User::class, AuthUser::class,
    Group::class, Member::class, Chat::class
], version = 8)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun authUserDao(): AuthUserDao
    abstract fun groupDao(): GroupDao
    abstract fun memberDao(): MemberDao
    abstract fun chatDao(): ChatDao

    companion object {
        val DATABASE_NAME = "222117056_tugas_prak_m7"
        var currentUser: User? = null;
        var activeUser: User? = null;
        var activeGroupId: Group? = null
        fun loginUser(user: User){
            currentUser = user;
        }
        fun logoutUser(){
            currentUser = null;
        }
    }
}