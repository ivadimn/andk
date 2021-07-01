package ru.ivadimn.material.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    private val isLoadingStateFlow = MutableStateFlow<Boolean>(false)
    val isLoadingFlow : StateFlow<Boolean>
        get() = isLoadingStateFlow

    fun loadImages() {
        Log.d("Material", "View model loadImages")
        if (!repository.isFirstRunning())
            return
        isLoadingStateFlow.value = true
        viewModelScope.launch {
            try {
                Log.d("Material", "View model start loading")
                repository.loadImages()
            }
            finally {
                isLoadingStateFlow.value = false
            }
        }
    }

}