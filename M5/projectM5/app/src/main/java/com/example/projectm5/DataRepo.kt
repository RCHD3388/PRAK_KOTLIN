package com.example.projectm5

class DataRepo {
    companion object{
        var datas = mutableListOf<Data>()
        lateinit var signedIn: Data;

        fun isValiduser(username: String, password: String): Boolean {
            return datas.any { it.username == username && it.password == password }
        }
        fun usernameExists(username: String): Boolean {
            return datas.any { it.username == username }
        }
        fun addData(username: String, name: String, password: String){
            datas.add(Data(username, name, password))
        }
        fun signIn(username: String){
            signedIn = datas.find { it.username == username }!!
        }
        fun signOut(){
            signedIn = Data("", "", "")
        }
    }
}