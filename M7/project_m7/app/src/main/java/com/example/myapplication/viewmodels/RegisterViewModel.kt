package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel() {

    private val _registerResult = MutableLiveData<Result<String>?>(null)
    val registerResult: LiveData<Result<String>?> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, username: String, password: String) {
        viewModelScope.launch {
            val result = AppConfiguration.authRepository.register(name, username, password)
            _registerResult.value = result
        }
    }

    fun clearRegisterResult() {
        _registerResult.value = null
    }
}
