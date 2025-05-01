package com.example.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "groups")
@JsonClass(generateAdapter = true)
data class Group(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    var name:String
)

// create group dro
@JsonClass(generateAdapter = true)
data class CreateGroupDro(
    val name: String,
    val members: List<String> // Daftar username anggota group
)


// create group response
@JsonClass(generateAdapter = true)
data class CreateGroupData(
    val group:Group,
    var users:List<User>,
    var members:List<Member>
)
@JsonClass(generateAdapter = true)
data class CreateGroupResponse(
    val message:String,
    var data:CreateGroupData,
)

// get group by user response
@JsonClass(generateAdapter = true)
data class GroupMultiData(
    val group:Group,
    var chat:List<Chat>? = null,
    var members:List<Member>,
    var users:List<User>,
)
@JsonClass(generateAdapter = true)
data class GroupMultiResponse(
    val message:String,
    var data:List<GroupMultiData>,
)

// get grouop information response
@JsonClass(generateAdapter = true)
data class GroupInformationResponse(
    val message:String,
    var data:GroupMultiData,
)

// response for repository fetch response
data class GroupFetchResponse(
    val group:Group,
    var last_chat:Chat?,
    var alternatif_name: String = ""
)

// response for repository single group response
data class GroupDataDto(
    val group:Group,
    var last_chat:Chat?,
    var alternatif_name: String = "",
    var chats: List<Chat>?
)