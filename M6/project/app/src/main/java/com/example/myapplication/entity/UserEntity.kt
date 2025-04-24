package com.example.myapplication.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username") val username:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "date_of_birth") val date_of_birth:String,
){
    override fun toString(): String {
        return "$name - $username"
    }
}