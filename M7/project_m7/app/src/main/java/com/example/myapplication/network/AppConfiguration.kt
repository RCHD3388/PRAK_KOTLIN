package com.example.myapplication.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.repository.FeatureRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppConfiguration {
    companion object{
        lateinit var authRepository: AuthRepository
        lateinit var featureRepository: FeatureRepository
        fun getApiService(context: Context){
            val roomDB = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build()

            authRepository = AuthRepository(roomDB, retrofit.create(WebService::class.java))
            featureRepository = FeatureRepository(roomDB, retrofit.create(WebService::class.java))
        }

        fun parseErrorMessage(json: String?): String? {
            return try {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(ErrorResponse::class.java)
                adapter.fromJson(json ?: "")?.error
            } catch (e: Exception) {
                null
            }
        }

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}