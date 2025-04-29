package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val _loginResult = MutableLiveData<Result<String>?>(null)
    val loginResult: LiveData<Result<String>?> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = AppConfiguration.authRepository.login(username, password)
            _loginResult.value = result
        }
    }

    fun clearRegisterResult() {
        _loginResult.value = null
    }
}
