package com.example.myapplication

import android.app.Application

class App: Application() {
    companion object{
        lateinit var db:AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        // baseContext.deleteDatabase("tugas_prak_m6")
        db = AppDatabase.getInstance(baseContext)
    }
}