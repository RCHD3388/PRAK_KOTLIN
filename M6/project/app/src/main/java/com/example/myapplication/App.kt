package com.example.myapplication

import android.app.Application

class App: Application() {
    companion object{
        lateinit var db:AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
         baseContext.deleteDatabase(AppDatabase.DATABASE_NAME)
        db = AppDatabase.getInstance(baseContext)
    }
}