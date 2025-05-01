package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.App
import com.example.myapplication.Model.Chat
import com.example.myapplication.Model.CreateChatDro
import com.example.myapplication.Model.CreateGroupDro
import com.example.myapplication.Model.CreateGroupResponse
import com.example.myapplication.Model.GroupDataDto
import com.example.myapplication.Model.GroupFetchResponse
import com.example.myapplication.Model.User
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import com.example.myapplication.network.WebService

class FeatureRepository(
    private val dataSourceLocal: AppDatabase,
    private val dataSourceRemote: WebService
) {
    suspend fun addFriend(
        data: CreateGroupDro
    ): Result<CreateGroupResponse> {
        if (!AppConfiguration.isInternetAvailable(App.instance.applicationContext)) {
            return Result.failure(Exception("Tidak ada koneksi internet."))
        }

        try {
            // Gunakan retrofit manual request jika body bukan User class
            val response = dataSourceRemote.createGroup(data)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    dataSourceLocal.groupDao().insert(body.data.group);
                    dataSourceLocal.userDao().insertUsers(body.data.users)
                    dataSourceLocal.memberDao().insertMembers(body.data.members)
                    return Result.success(body)
                } else {
                    return Result.failure(Exception("Respon tidak valid dari server."))
                }
            } else {
                // Ambil pesan error dari body JSON
                val errorBody = response.errorBody()?.string()
                val message = AppConfiguration.parseErrorMessage(errorBody) ?: "Terjadi kesalahan saat add friend."
                return Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun fetchGroupByUser(username: String, keyword: String): Result<List<GroupFetchResponse>> {
        try {
            if (AppConfiguration.isInternetAvailable(App.instance.applicationContext)) {
                // fetch group data from server
                val response = dataSourceRemote.getGroupByUser(username)
                val body = response.body()
                if (body != null) {
                    body.data.forEach {
                        dataSourceLocal.groupDao().insert(it.group)
                        dataSourceLocal.userDao().insertUsers(it.users)
                        dataSourceLocal.memberDao().insertMembers(it.members)
                        if (it.chat != null && it.chat!!.isNotEmpty()) {
                            dataSourceLocal.chatDao().insertChats(it.chat!!)
                        }
                    }
                } else {
                    Log.d("fetchGroupByUser", "Response body is null")
                }
            }
        } catch (e: Exception) {
            Log.d("FETCHER_ERROR", "Exception: ${e.message}")
            return Result.failure(e)
        }
        val members = dataSourceLocal.memberDao().fetchByUser(username)
        val groups = dataSourceLocal.groupDao().fetchGroupsByIds(members.map { it.group_id })

        val formatedGroup = groups.map {
            val chat = dataSourceLocal.chatDao().fetchLastChat(it.id)
            var alternatif_name = it.name;
            if(alternatif_name == ""){
                val privateMembers = dataSourceLocal.memberDao().fetchByGroupForName(it.id, username);
                val user = dataSourceLocal.userDao().get(privateMembers[0].user_username);
                alternatif_name = user?.name ?: it.name;
            }
            GroupFetchResponse(it, chat, alternatif_name)
        }.filter {
            it.alternatif_name.contains(keyword);
        }.sortedByDescending { it.last_chat?.chat_time };

        return Result.success(formatedGroup)
    }

    suspend fun fetchUserByUsername(username: String): Result<User> {
        try {
            if (AppConfiguration.isInternetAvailable(App.instance.applicationContext)) {
                // fetch username from server
                val response = dataSourceRemote.getUsers(username)
                val body = response.body()
                if (body != null && body.data != null) {
                    dataSourceLocal.userDao().insert(body.data!!)
                } else {
                    Log.d("fetchGroupByUser", "Response body is null")
                }
            }
        } catch (e: Exception) {
            Log.d("FETCHER_ERROR", "Exception: ${e.message}")
            return Result.failure(e)
        }
        val user = dataSourceLocal.userDao().get(username)
        if (user != null){
            return Result.success(user)
        }else{
            return Result.failure(Exception("User dengan username tersebut tidak ditemukan"))
        }
    }

    suspend fun fetchGroupInformationById(groupId: Int): Result<GroupDataDto> {
        try {
            if (AppConfiguration.isInternetAvailable(App.instance.applicationContext)) {
                // fetch username from server
                val response = dataSourceRemote.getGroupInformation(groupId)
                val body = response.body()
                if (body != null && body.data != null) {
                    dataSourceLocal.groupDao().insert(body.data.group)
                    dataSourceLocal.userDao().insertUsers(body.data.users)
                    dataSourceLocal.memberDao().insertMembers(body.data.members)
                    if (body.data.chat != null && body.data.chat!!.isNotEmpty()) {
                        dataSourceLocal.chatDao().insertChats(body.data.chat!!)
                    }
                } else {
                    Log.d("fetchGroupByUser", "Response body is null")
                }
            }
        } catch (e: Exception) {
            Log.d("FETCHER_ERROR", "Exception: ${e.message}")
             return Result.failure(e)
        }
        val group = dataSourceLocal.groupDao().fetchOne(groupId)
        if(group != null){
            val last_chat = dataSourceLocal.chatDao().fetchLastChat(group.id)
            val chats = dataSourceLocal.chatDao().fetchGroupsByIds(group.id)
            var alternatif_name = group.name;
            if(alternatif_name == ""){
                val privateMembers = dataSourceLocal.memberDao().fetchByGroupForName(group.id, AppDatabase.currentUser?.username ?: "");
                val user = dataSourceLocal.userDao().get(privateMembers[0].user_username);
                alternatif_name = user?.name ?: group.name;
            }
            val formatedData = GroupDataDto(group, last_chat, alternatif_name, chats);
            return Result.success(formatedData)
        }else{
            return Result.failure(Exception("Group dengan id tersebut tidak ditemukan"))
        }
    }

    suspend fun addNewChat(
        groupId: Int,
        data: CreateChatDro
    ): Result<Chat> {
        if (!AppConfiguration.isInternetAvailable(App.instance.applicationContext)) {
            return Result.failure(Exception("Tidak ada koneksi internet."))
        }

        try {
            // Gunakan retrofit manual request jika body bukan User class
            val response = dataSourceRemote.createGroupChat(groupId, data)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    dataSourceLocal.chatDao().insert(body.data);
                    return Result.success(body.data)
                } else {
                    return Result.failure(Exception("Respon tidak valid dari server."))
                }
            } else {
                // Ambil pesan error dari body JSON
                val errorBody = response.errorBody()?.string()
                val message = AppConfiguration.parseErrorMessage(errorBody) ?: "Terjadi kesalahan saat add chat."
                return Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getPrivateXUser(groupId: Int, username: String): Result<User>{
        val members = dataSourceLocal.memberDao().fetchByGroupId(groupId).filter { it.user_username != username };
        val user = fetchUserByUsername(members[0].user_username);
        return user;
    }
}