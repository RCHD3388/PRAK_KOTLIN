package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.App
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.launch

class UserRegisterViewModel: ViewModel() {
    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>>
        get() = _users

    private val _registerStatus = MutableLiveData<String>("")
    val registerStatus: LiveData<String>
        get() = _registerStatus


    private fun refreshList(){
        viewModelScope.launch {
            _users.value = App.db.userDao().fetch()
        }
    }

    fun init(){
        refreshList()
    }

    fun registerUser(username: String, name: String, password: String, dateOfBirth: String) {
        viewModelScope.launch {
            val user = App.db.userDao().get(username)
            if (user == null) {
                App.db.userDao().insert(UserEntity(username, password, name, dateOfBirth))
                _registerStatus.value = "Berhasil melakukan register";
            }else{
                _registerStatus.value = "User telah terdaftar pada App"
            }
        }
    }
}