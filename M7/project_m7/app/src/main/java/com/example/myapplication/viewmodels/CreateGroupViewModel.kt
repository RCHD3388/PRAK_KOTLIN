package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.GroupFetchResponse
import com.example.myapplication.Model.User
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class CreateGroupViewModel() : ViewModel() {

    private val _targetUserList = MutableLiveData<List<User>>()
    val targetUserList: LiveData<List<User>> = _targetUserList

    fun addTargetUser(username: String) {
        viewModelScope.launch {

        }
    }

    fun createGroup(groupName: String) {
        viewModelScope.launch {

        }
    }
}