package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.CreateGroupDro
import com.example.myapplication.Model.CreateGroupResponse
import com.example.myapplication.Model.GroupFetchResponse
import com.example.myapplication.Model.User
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class CreateGroupViewModel() : ViewModel() {

    private val _targetUserList = MutableLiveData<MutableList<User>>(
        if(AppDatabase.currentUser != null){
            mutableListOf(AppDatabase.currentUser!!)
        }else {
            mutableListOf()
        }
    )
    val targetUserList: LiveData<MutableList<User>> = _targetUserList

    private val _addTargetUserResult = MutableLiveData<Result<User>>()
    val addTargetUserResult: LiveData<Result<User>> = _addTargetUserResult

    private val _createGroupResult = MutableLiveData<Result<CreateGroupResponse>?>()
    val createGroupResult: LiveData<Result<CreateGroupResponse>?> = _createGroupResult

    fun addTargetUser(username: String) {
        if(username == AppDatabase.currentUser!!.username){
            _addTargetUserResult.value = Result.failure(Exception("Anda otomatis sudah menjadi anggota dari group ini"))
            return
        }
        val currentList = _targetUserList.value ?: mutableListOf()
        if (!currentList.none { it.username == username }) {
            _addTargetUserResult.value = Result.failure(Exception("User sudah ada dalam daftar anggota"))
            return
        }else{
            viewModelScope.launch {
                val response = AppConfiguration.featureRepository.fetchUserByUsername(username);
                response.onSuccess {
                    currentList.add(it)
                    _targetUserList.value = currentList // Update LiveData
                }
                _addTargetUserResult.value = response
            }
        }
    }

    fun createGroup(groupName: String) {
        if(_targetUserList.value!!.size < 2){
            _createGroupResult.value = Result.failure(Exception("Group member harus diisi"))
            return
        }
        viewModelScope.launch {
            val response = AppConfiguration.featureRepository.addFriend(CreateGroupDro(
                name = groupName,
                members = _targetUserList.value!!.map { it.username }
            ))
            _createGroupResult.value = response
        }
    }

    fun removeTargetUser(user: User){
        val currentList = _targetUserList.value ?: mutableListOf()
        currentList.remove(user)
        _targetUserList.value = currentList
    }
}