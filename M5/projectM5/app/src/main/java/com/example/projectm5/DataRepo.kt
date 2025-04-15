package com.example.projectm5

class DataRepo {
    companion object{
        var datas = mutableListOf<Data>()

        fun isValiduser(username: String, password: String): Boolean {
            return datas.any { it.username == username && it.password == password }
        }
        fun addData(username: String, name: String, password: String){
            datas.add(Data(username, name, password))
        }
    }
}