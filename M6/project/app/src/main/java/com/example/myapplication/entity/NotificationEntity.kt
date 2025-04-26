package com.example.myapplication.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notifs")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notif_id") val notif_id: Long = 0,
    @ColumnInfo(name = "from_username") val from_username:String,
    @ColumnInfo(name = "to_username") val to_username:String,
    @ColumnInfo(name = "note") var note:String,
):Parcelable{
    override fun toString(): String {
        return "${note}";
    }
}