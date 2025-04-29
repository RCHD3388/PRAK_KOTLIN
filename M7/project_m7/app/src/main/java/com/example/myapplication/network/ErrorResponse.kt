package com.example.myapplication.network

import com.squareup.moshi.JsonClass

// Kelas data error
@JsonClass(generateAdapter = true)
data class ErrorResponse(val error: String)