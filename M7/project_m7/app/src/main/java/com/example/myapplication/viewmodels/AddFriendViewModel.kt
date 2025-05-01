package com.example.myapplication.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.CreateGroupDro
import com.example.myapplication.Model.CreateGroupResponse
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class AddFriendViewModel() : ViewModel() {

    private val _addFriendResult = MutableLiveData<Result<CreateGroupResponse>?>(null)
    val addFriendResult: LiveData<Result<CreateGroupResponse>?> = _addFriendResult

    fun addFriend(username: String) {
        viewModelScope.launch {
            var result = AppConfiguration.featureRepository.addFriend(CreateGroupDro(
                name = "",
                members = listOf(AppDatabase.currentUser!!.username, username)
            ))
            _addFriendResult.value = result
        }
    }

    fun clearResult() {
        _addFriendResult.value = null
    }
}