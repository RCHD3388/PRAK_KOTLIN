package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.App
import com.example.myapplication.entity.TweetEntity
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

    fun defaultSetup(){
        viewModelScope.launch {
            if(App.db.userDao().fetch().isEmpty()){
                // ADD 3 DUMMY USER
                App.db.userDao().insert(UserEntity("@richard", "password", "Richard Rafer Guy", "1 Mei 2004"))
                App.db.userDao().insert(UserEntity("@babigila", "password", "Michael Steven", "2 Feb 2003"))
                App.db.userDao().insert(UserEntity("@anakhilang", "password", "William", "3 Jan 2001"))

                // ADD 6 TWEET PADA MASING MASING USER
                App.db.tweetDao().insert(TweetEntity(
                    user_username = "@richard", user_name = "Richard Rafer Guy", tweet = "Halo para babi, praktikum ini sangat menyenangkan",
                    like = 0, comment = 0, retweet = 0)
                )
                App.db.tweetDao().insert(TweetEntity(
                    user_username = "@richard", user_name = "Richard Rafer Guy", tweet = "Halo babiq, praktikum ini mengerika",
                    like = 0, comment = 0, retweet = 0)
                )
                App.db.tweetDao().insert(TweetEntity(
                    user_username = "@babigila", user_name = "Michael Steven", tweet = "Saya orang kurang gizi gaes",
                    like = 0, comment = 0, retweet = 0)
                )
                App.db.tweetDao().insert(TweetEntity(
                    user_username = "@babigila", user_name = "Michael Steven", tweet = "Saya suka sama sutikno",
                    like = 0, comment = 0, retweet = 0)
                )
                App.db.tweetDao().insert(TweetEntity(
                    user_username = "@anakhilang", user_name = "William", tweet = "Tolong temukan saya, saya hilang",
                    like = 0, comment = 0, retweet = 0)
                )
                App.db.tweetDao().insert(TweetEntity(
                    user_username = "@anakhilang", user_name = "William", tweet = "Saya adalah anak hilang umur 102 gaes",
                    like = 0, comment = 0, retweet = 0)
                )
            }
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val user = App.db.userDao().get(username)
            if (user != null) {
                if (user.password == password) {
                    COlogin(user);
                    _loggedInStatus.value = "Berhasil melakukan login";
                    refreshList()
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
        var COActivateUser: UserEntity = UserEntity("", "", "", "")

        fun COlogout(){
            COloggedInStatus = false;
            COloggedinUser = UserEntity("", "", "", "")
        }
        fun COlogin(user: UserEntity){
            COloggedInStatus = true;
            COloggedinUser = user;
        }
        fun setActiveUser(user: UserEntity){
            COActivateUser = user;
        }
    }
}