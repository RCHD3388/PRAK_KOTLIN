package com.example.projectm5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Locale

class DataViewModel: ViewModel() {
    private val _currenAddTransState: MutableLiveData<String> = MutableLiveData("pemasukan")
    val currentAddTransState: LiveData<String>
        get() = _currenAddTransState

    private val _totalHarga: MutableLiveData<String> = MutableLiveData("0")
    val totalHarga: LiveData<String>
        get() = _totalHarga

    fun setCurrentAddTransState(state: String){
        _currenAddTransState.value = state
    }

    fun setTotalHarga(harga: String){
        _totalHarga.value = harga
    }
}