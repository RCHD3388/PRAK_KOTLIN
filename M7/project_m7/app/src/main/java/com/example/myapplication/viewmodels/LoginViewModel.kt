package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.LogRegResponse
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LogRegResponse>?>(null)
    val loginResult: LiveData<Result<LogRegResponse>?> = _loginResult

    private val _isAlreadLogin = MutableLiveData<Boolean>(false)
    val isAlreadLogin: LiveData<Boolean> = _isAlreadLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            val result = AppConfiguration.authRepository.login(username, password)
            result.onSuccess {
                if (rememberMe) {
                    AppConfiguration.authRepository.saveLoginSession(username)
                }
                // set current login user
                AppDatabase.loginUser(it.data!!)
            }
            _loginResult.value = result
        }
    }

    fun checkLoggedInState(){
        viewModelScope.launch {
            val result = AppConfiguration.authRepository.isLoggedInBefore()
            if(result){
                val currentLoggedInUser = AppConfiguration.authRepository.getLoggedInUser()!!
                AppDatabase.loginUser(currentLoggedInUser)
            }
            _isAlreadLogin.value = result;
        }
    }

    fun clearRegisterResult() {
        _loginResult.value = null
    }
}
