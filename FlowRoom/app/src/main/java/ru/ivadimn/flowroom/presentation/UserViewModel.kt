package ru.ivadimn.flowroom.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import ru.ivadimn.flowroom.model.Sex
import ru.ivadimn.flowroom.model.User

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    private val usersLiveData = MutableStateFlow(emptyList<User>())
    val users : Flow<List<User>>
    get() = usersLiveData

    fun insert(name : String, age: Int, sex: Sex) {
        viewModelScope.launch {
            repository.insert(name, age, sex)
        }
    }

    fun selectAll() {
        viewModelScope.launch {
            usersLiveData
                .emitAll(repository.selectAllFlow())
        }
    }
}