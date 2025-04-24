package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.App
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.launch

class UserLoginViewModel: ViewModel() {
    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>>
        get() = _users

    private val _loggedInStatus = MutableLiveData<String>("")
    val loggedInStatus: LiveData<String>
        get() = _loggedInStatus


    private fun refreshList(){
        viewModelScope.launch {
            _users.value = App.db.userDao().fetch()
        }
    }

    fun init(){
        refreshList()
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val user = App.db.userDao().get(username)
            if (user != null) {
                if (user.password == password) {
                    COlogin(user);
                    _loggedInStatus.value = "Berhasil melakukan login";
                }else{
                    _loggedInStatus.value = "Gagal login, username atau password salah";
                }
            }else{
                _loggedInStatus.value = "Gagal login, username atau password salah";
            }
        }
    }

    companion object{
        var COloggedInStatus = false;
        var COloggedinUser: UserEntity = UserEntity("", "", "", "")

        fun COlogout(){
            COloggedInStatus = false;
            COloggedinUser = UserEntity("", "", "", "")
        }
        fun COlogin(user: UserEntity){
            COloggedInStatus = true;
            COloggedinUser = user;
        }
    }
}