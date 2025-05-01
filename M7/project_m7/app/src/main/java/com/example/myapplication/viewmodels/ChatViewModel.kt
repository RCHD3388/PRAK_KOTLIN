package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.Chat
import com.example.myapplication.Model.CreateChatDro
import com.example.myapplication.Model.Group
import com.example.myapplication.Model.GroupDataDto
import com.example.myapplication.adapters.HomeGroupAdapter
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class ChatViewModel() : ViewModel() {

    private val _group = MutableLiveData<GroupDataDto>()
    val group: LiveData<GroupDataDto> = _group

    private val _activeUserState = MutableLiveData<Boolean>(false)
    val activeUserState: LiveData<Boolean> = _activeUserState

    private val _resultAddChat = MutableLiveData<Result<Chat>>()
    val resultAddChat: LiveData<Result<Chat>> = _resultAddChat

    fun refreshAllDataData(){
        val currentGroup = AppDatabase.activeGroupId;
        if(currentGroup!=null) {
            viewModelScope.launch {
                val response = AppConfiguration.featureRepository.fetchGroupInformationById(currentGroup.id)
                response.onSuccess {
                    _group.value = it;
                }
            }
        }
    }

    fun addNewChat(chat: String){
        viewModelScope.launch {
            if(AppDatabase.activeGroupId != null) {
                val response = AppConfiguration.featureRepository.addNewChat(
                    AppDatabase.activeGroupId!!.id, CreateChatDro(
                        AppDatabase.currentUser?.username ?: "",
                        chat,
                        System.currentTimeMillis()
                    )
                )
                response.onSuccess {
                    refreshAllDataData()
                }
                _resultAddChat.value = response
            }
        }
    }

    fun isPrivateChat(): Boolean{
        return _group.value?.group?.name != _group.value?.alternatif_name;
    }

    fun setActiveUsername(username: String = ""){
        viewModelScope.launch {
            if(username.isNotEmpty()) {
                val response = AppConfiguration.featureRepository.fetchUserByUsername(username)
                response.onSuccess {
                    AppDatabase.activeUser = it;
                    _activeUserState.value = true;
                }
            }else{
                val response = AppConfiguration.featureRepository.getPrivateXUser(
                    _group.value?.group?.id ?: AppDatabase.activeGroupId!!.id, AppDatabase.currentUser?.username ?: ""
                )
                response.onSuccess {
                    AppDatabase.activeUser = it;
                    _activeUserState.value = true;
                }
            }
        }
    }
    fun resetActiveUserState(){
        _activeUserState.value = false;
    }
}