package ru.ivadimn.timer.ui

import android.service.autofill.ImageTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.ivadimn.timer.model.CountdownRepository

class CountdownViewModel : ViewModel() {

    private val repository = CountdownRepository()

    private val countdownScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)


    private val countSecondsFlow = MutableStateFlow<Int>(0)
    val countSeconds : Flow<Int>
        get() = countSecondsFlow


    fun setTotalSeconds(seconds : Int) {
        repository.seTotalSeconds(seconds)
    }

    fun startCountdown() {
        viewModelScope.launch {
            var current = repository.getCurrentSeconds()
            countdownScope.launch {
                while (isActive && current >= 0) {
                    countSecondsFlow.emit(current)
                    delay(1000)
                    current--
                }
                repository.setCurrentSeconds(current)
            }
        }
    }
    fun pauseCountdown() {
        countdownScope.coroutineContext.cancelChildren()
    }

}