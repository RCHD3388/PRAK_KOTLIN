package com.example.myapplication.network

import com.example.myapplication.Model.LogRegDro
import com.example.myapplication.Model.LogRegResponse
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
}