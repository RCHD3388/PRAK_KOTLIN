package com.example.projectm5

data class Data(
    var username: String,
    var name: String,
    var password: String,
    var accounts: MutableList<Account> = mutableListOf<Account>(
        Account("Cash", 0),
        Account("JalanPay", 0),
        Account("ShoopePay", 0),
        Account("HapePay", 0),
        Account("Debit Card", 0),
    ),
    var pemasukan: Int = 0,
    var pengeluaran: Int = 0,
    var transactions: MutableList<Transaction> = mutableListOf<Transaction>()
) {

}