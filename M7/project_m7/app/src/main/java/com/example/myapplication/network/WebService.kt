package com.example.myapplication.network

import com.example.myapplication.Model.CreateGroupDro
import com.example.myapplication.Model.CreateGroupResponse
import com.example.myapplication.Model.GroupChatResponse
import com.example.myapplication.Model.GroupMultiResponse
import com.example.myapplication.Model.GroupSingleResponse
import com.example.myapplication.Model.LogRegDro
import com.example.myapplication.Model.LogRegResponse
import com.example.myapplication.Model.SingleChatResponse
import com.example.myapplication.Model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @POST("login")
    suspend fun loginUser(@Body user:LogRegDro):Response<LogRegResponse>

    @POST("register")
    suspend fun registerUser(@Body user:User):Response<LogRegResponse>

    @GET("users/{username}")
    suspend fun getUsers(@Path("username") username:String):Response<User>
    @POST("group")
    suspend fun createGroup(@Body group:CreateGroupDro):Response<CreateGroupResponse>
    @GET("users/{username}/groups")
    suspend fun getGroupByUser(@Path("username") username:String):Response<GroupMultiResponse>
    @GET("group/:group_id/chat")
    suspend fun getGroupChat(@Path("group_id") groupId:Int):Response<SingleChatResponse>
    @POST("group/:group_id/chat")
    suspend fun createGroupChat(@Path("group_id") groupId:Int, @Body chat:CreateGroupDro):Response<GroupChatResponse>
}