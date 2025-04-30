package com.example.myapplication.repository

import android.app.Application
import android.content.Context
import com.example.myapplication.App
import com.example.myapplication.Model.AuthUser
import com.example.myapplication.Model.LogRegDro
import com.example.myapplication.Model.LogRegResponse
import com.example.myapplication.Model.User
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import com.example.myapplication.network.WebService

class AuthRepository(
    private val dataSourceLocal: AppDatabase,
    private val dataSourceRemote: WebService
) {
    suspend fun register(
        name: String,
        username: String,
        password: String
    ): Result<String> {

        if (!AppConfiguration.isInternetAvailable(App.instance.applicationContext)) {
            return Result.failure(Exception("Tidak ada koneksi internet."))
        }

        try {
            // Data dikirim sesuai kebutuhan backend kamu
            val newUser = User(name = name, username = username, password = password);

            // Gunakan retrofit manual request jika body bukan User class
            val response = dataSourceRemote.registerUser(newUser)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    dataSourceLocal.userDao().insert(body.data!!)
                    return Result.success(body.message)
                } else {
                    return Result.failure(Exception("Respon tidak valid dari server."))
                }
            } else {
                // Ambil pesan error dari body JSON
                val errorBody = response.errorBody()?.string()
                val message = AppConfiguration.parseErrorMessage(errorBody) ?: "Terjadi kesalahan saat registrasi."
                return Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun login(
        username: String,
        password: String,
    ): Result<LogRegResponse> {
        if (!AppConfiguration.isInternetAvailable(App.instance.applicationContext)) {
            return Result.failure(Exception("Tidak ada koneksi internet."))
        }

        try {
            // Data dikirim sesuai kebutuhan backend kamu
            val loginUserData = LogRegDro(username = username, password = password);

            // Gunakan retrofit manual request jika body bukan User class
            val response = dataSourceRemote.loginUser(loginUserData)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    // add to local
                    dataSourceLocal.userDao().insert(body.data!!)
                    // return success response
                    return Result.success(body)
                } else {
                    return Result.failure(Exception("Respon tidak valid dari server."))
                }
            } else {
                // Ambil pesan error dari body JSON
                val errorBody = response.errorBody()?.string()
                val message = AppConfiguration.parseErrorMessage(errorBody) ?: "Terjadi kesalahan saat login."
                return Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun saveLoginSession(username: String){
        dataSourceLocal.authUserDao().saveSession(
            AuthUser(
                "LOGIN_SESSION",
                username,
                System.currentTimeMillis()
            )
        )
    }
    suspend fun isLoggedInBefore(): Boolean {
        val authUser = dataSourceLocal.authUserDao().getCurrentAuth("LOGIN_SESSION")
        if(authUser != null){
            val returnValue = authUser.loginTimestamp + (24 * 60 * 60 * 1000) > System.currentTimeMillis()
            if(!returnValue){
                clearSession()
            }
            return returnValue;
        }
        return false;
    }
    suspend fun getLoggedInUser(): User? {
        val authUser = dataSourceLocal.authUserDao().getCurrentAuth("LOGIN_SESSION")
        return dataSourceLocal.userDao().get(authUser?.username ?: "");
    }
    suspend fun clearSession() {
        dataSourceLocal.authUserDao().clearSession();
    }
}