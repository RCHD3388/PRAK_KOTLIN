package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.App
import com.example.myapplication.entity.NotificationEntity
import com.example.myapplication.entity.TweetEntity
import com.example.myapplication.entity.UTLikeEntity
import com.example.myapplication.entity.UTRetweetEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.entity.dto.UserTweetDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotifViewModel: ViewModel() {
    private val _notifs = MutableLiveData<List<NotificationEntity>>()
    val notifs: LiveData<List<NotificationEntity>>
        get() = _notifs

    private fun refreshList(){
        viewModelScope.launch {
            val user = UserLoginViewModel.COloggedinUser
            _notifs.value = App.db.notificationDao().fetchByUsernameReceiver(user.username)
        }
    }

    fun init(){
        refreshList()
    }
}