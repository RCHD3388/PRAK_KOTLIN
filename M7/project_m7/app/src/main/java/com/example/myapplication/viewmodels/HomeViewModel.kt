package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.GroupFetchResponse
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    private val _groupLists = MutableLiveData<List<GroupFetchResponse>>()
    val groupLists: LiveData<List<GroupFetchResponse>> = _groupLists

    fun refreshGroupList(keyword: String = "") {
        viewModelScope.launch {
            val result = AppConfiguration.featureRepository.fetchGroupByUser(AppDatabase.currentUser!!.username, keyword);
            result.onSuccess {
                _groupLists.value = it;
            }
        }
    }
}