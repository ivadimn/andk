package com.example.retrofit.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.model.RemoteUser
import com.example.retrofit.model.UserRepository

class UserListViewModel : ViewModel() {

    private val repository = UserRepository()

    private val usersLiveData = MutableLiveData<List<RemoteUser>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val users : LiveData<List<RemoteUser>>
    get() = usersLiveData

    val isLoading : LiveData<Boolean>
    get() = isLoadingLiveData

    fun searchUsers(query : String) {
        isLoadingLiveData.postValue(true)
        repository.searchUsers(query = query,
        onComplete = { listUsers ->
            isLoadingLiveData.postValue(false)
            usersLiveData.postValue(listUsers)
        },
        onError = {
            isLoadingLiveData.postValue(false)
            usersLiveData.postValue(emptyList())
        }
        )
    }

}