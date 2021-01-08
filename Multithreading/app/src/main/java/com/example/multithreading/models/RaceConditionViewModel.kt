package com.example.multithreading.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.multithreading.repositories.CalcRepository

class RaceConditionViewModel : ViewModel() {

    private var numberLiveData = MutableLiveData<Long>(0)
    private val calcRepository = CalcRepository()

    val number : LiveData<Long>
    get() = numberLiveData

    fun calculate() {
        calcRepository.calculate { value ->
            numberLiveData.postValue(value)
        }
    }
}