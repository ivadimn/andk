package com.example.db.presentation.listusers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.db.database.model.user.User
import com.example.db.database.model.user.UsersRepository
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {
    private val repository = UsersRepository()

    private val userListLiveData = MutableLiveData<List<User>>()
    val userList : LiveData<List<User>>
    get() = userListLiveData

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                userListLiveData.postValue(repository.getAllUsers())
            }
            catch (t : Throwable) {
                userListLiveData.postValue(emptyList())
            }
        }
    }

    fun removeUser(user: User) {
        viewModelScope.launch {
            try {
                repository.removeUser(user)
                getAllUsers()
            }
            catch (t : Throwable) {
                Log.d("User list", "Error - ${t.message}")
            }
        }
    }
}