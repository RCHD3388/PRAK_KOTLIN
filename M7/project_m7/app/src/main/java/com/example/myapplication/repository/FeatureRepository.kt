package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.App
import com.example.myapplication.Model.CreateGroupDro
import com.example.myapplication.Model.CreateGroupResponse
import com.example.myapplication.Model.GroupFetchResponse
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
}