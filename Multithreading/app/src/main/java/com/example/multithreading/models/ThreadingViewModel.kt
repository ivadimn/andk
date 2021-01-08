package com.example.multithreading.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.multithreading.repositories.DataRepository

class ThreadingViewModel : ViewModel() {

    private val dataRepository = DataRepository()

    private val dataLiveData = MutableLiveData<String>()
    private val timeLiveData = MutableLiveData<Long>()

    val data : LiveData<String>
    get() = dataLiveData

    val time : LiveData<Long>
    get() = timeLiveData

    fun getData() {
        dataRepository.getDataEx(56) {
            data, time ->
                dataLiveData.postValue(data)
                timeLiveData.postValue(time)
        }
    }

}