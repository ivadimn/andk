package com.example.files.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.files.data.DataStoreRepository
import kotlinx.coroutines.launch

class DataStoreViewModel : ViewModel() {

    private val repository = DataStoreRepository()

    val textLiveData: LiveData<String>
    get() = repository.get().asLiveData()

    fun save(text : String) {
        viewModelScope.launch {
            repository.save(text)
        }
    }
}