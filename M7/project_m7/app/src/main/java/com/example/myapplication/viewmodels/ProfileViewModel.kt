package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {

    private val _logoutResult = MutableLiveData<Boolean>(false)
    val logoutResult: LiveData<Boolean> = _logoutResult

    fun logout() {
        viewModelScope.launch {
            AppDatabase.logoutUser();
            AppConfiguration.authRepository.clearSession()
            _logoutResult.value = true
        }
    }
}