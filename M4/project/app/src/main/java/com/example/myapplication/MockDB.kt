package com.example.myapplication

class MockDB {
    companion object{
        var users = mutableListOf<User>()
        var loggedInUser: User? = null
    }
}