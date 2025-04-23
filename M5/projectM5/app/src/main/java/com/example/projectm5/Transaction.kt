package com.example.projectm5

import java.util.Calendar

data class Transaction(
    val type: String,
    val amount: Int,
    val category: String,
    val account: String,
    val note: String,
    val date: Calendar
) {
}