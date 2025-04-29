package com.example.myapplication

import android.app.Application
import com.example.myapplication.network.AppConfiguration

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppConfiguration.getApiService(this)
    }
}