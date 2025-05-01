package com.example.myapplication.network

import com.example.myapplication.Model.CreateChatDro
import com.example.myapplication.Model.CreateGroupDro
import com.example.myapplication.Model.CreateGroupResponse
import com.example.myapplication.Model.GroupInformationResponse
import com.example.myapplication.Model.GroupMultiResponse
import com.example.myapplication.Model.LogRegDro
import com.example.myapplication.Model.LogRegResponse
import com.example.myapplication.Model.CreateChatResponse
import com.example.myapplication.Model.SingleUserResponse
import com.example.myapplication.Model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WebService {
    @POST("login")
    suspend fun loginUser(@Body user:LogRegDro):Response<LogRegResponse>

    @POST("register")
    suspend fun registerUser(@Body user:User):Response<LogRegResponse>

    @GET("users/{username}")
    suspend fun getUsers(@Path("username") username:String):Response<SingleUserResponse>
    @POST("group")
    suspend fun createGroup(@Body group:CreateGroupDro):Response<CreateGroupResponse>
    @GET("users/{username}/groups")
    suspend fun getGroupByUser(@Path("username") username:String):Response<GroupMultiResponse>
    @GET("group/{group_id}")
    suspend fun getGroupInformation(@Path("group_id") groupId:Int):Response<GroupInformationResponse>
    @POST("group/{group_id}/chat")
    suspend fun createGroupChat(@Path("group_id") groupId:Int, @Body chat:CreateChatDro):Response<CreateChatResponse>
}