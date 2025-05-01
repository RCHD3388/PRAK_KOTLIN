package com.example.myapplication.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.Model.Group
import com.example.myapplication.Model.Member
import com.example.myapplication.Model.User

@Dao
interface MemberDao {
    @Insert()
    suspend fun insert(group: Member)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(groups: List<Member>)

    @Query("SELECT * FROM members where group_id = :groupId")
    suspend fun fetchByGroupId(groupId: Int):List<Member>

    @Query("SELECT * FROM members WHERE user_username = :username")
    suspend fun fetchByUser(username: String): List<Member>

    @Query("SELECT * FROM members WHERE group_id = :groupId AND user_username <> :username")
    suspend fun fetchByGroupForName(groupId: Int, username: String): List<Member>
}