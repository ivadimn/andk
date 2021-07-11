package ru.ivadimn.di.ui.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.ivadimn.di.data.entities.User
import ru.ivadimn.di.interfaces.UserRepository

class UserViewModel(
    private val repository : UserRepository
) : ViewModel() {

    init {
        Log.d("DINJ", "init view model - $this")
    }

    private val userListStateFlow = MutableStateFlow<List<User>>(emptyList())
    val userListFlow : Flow<List<User>>
    get() = userListStateFlow

    private val errorMessageStateFlow = MutableStateFlow<String>("")
    val errorMessageFlow : Flow<String>
        get() = errorMessageStateFlow


    fun selectAll() {
        viewModelScope.launch {
           userListStateFlow.emit(repository.selectAll())
        }
    }

}