package com.example.myapplication.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.Model.Group

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: Group)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroups(groups: List<Group>)

    @Query("SELECT * FROM groups")
    suspend fun fetch():List<Group>

    @Query("SELECT * FROM groups WHERE id IN (:groupIds)")
    suspend fun fetchGroupsByIds(groupIds: List<Int>): List<Group>

    @Query("SELECT * FROM groups where id = :id")
    suspend fun fetchOne(id:Int): Group?
}