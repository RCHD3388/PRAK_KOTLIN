package com.example.myapplication

data class User (
    var username: String,
    var password: String,
    var name: String = "",
    var dateOfBirth: String = "",
    var phoneNumber: String = ""
)