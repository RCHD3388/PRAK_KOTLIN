package com.example.myapplication

import android.app.Application
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration

class App: Application() {
    companion object {
        lateinit var instance: App
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppConfiguration.getApiService(this)
    }
}