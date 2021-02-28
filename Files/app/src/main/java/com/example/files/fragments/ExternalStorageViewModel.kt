package com.example.files.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.files.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExternalStorageViewModel : ViewModel() {

    private val repository = Repository()

    private val isLoadedLiveData = MutableLiveData<Boolean>()

    val isLoaded : LiveData<Boolean>
    get() = isLoadedLiveData

    fun getFile(url : String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getFile(url)) {
                isLoadedLiveData.postValue(true)
            }
            else {
                isLoadedLiveData.postValue(false)
            }
        }
    }

}