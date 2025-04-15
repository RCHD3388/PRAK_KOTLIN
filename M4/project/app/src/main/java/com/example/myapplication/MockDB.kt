package com.example.myapplication

class MockDB {
    companion object{
        var users = mutableListOf<User>(User("admin", "admin", "admin", "admin"))
        var loggedInUser: User? = null
    }
}