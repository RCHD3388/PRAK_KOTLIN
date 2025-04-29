package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.Model.LogRegDro
import com.example.myapplication.Model.User
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import com.example.myapplication.network.WebService

class AuthRepository(
    private val dataSourceLocal: AppDatabase,
    private val dataSourceRemote: WebService,
    private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    suspend fun register(
        name: String,
        username: String,
        password: String
    ): Result<String> {

        if (!AppConfiguration.isInternetAvailable(context)) {
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
                    dataSourceLocal.userDao().insert(body.data)
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
    ): Result<String> {
        if (!AppConfiguration.isInternetAvailable(context)) {
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

                    return Result.success(body.message)
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

    fun saveLoginSession(rememberMeStats: Boolean, username: String){
        if(rememberMeStats){
            val editorPreferences = prefs.edit();
            editorPreferences.putString("username", username);
            editorPreferences.putLong("login_time", System.currentTimeMillis());
            editorPreferences.apply()
        }
    }
    fun isLoggedInBefore(): Boolean {
        val loginTime = prefs.getLong("login_time", 0L)
        val oneDayMillis = 24 * 60 * 60 * 1000L
        return System.currentTimeMillis() - loginTime < oneDayMillis
    }
    fun getLoggedInUsername(): String? {
        return prefs.getString("username", null)
    }
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}