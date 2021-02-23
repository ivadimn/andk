package com.example.corutine.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corutine.model.RemoteUser
import com.example.corutine.model.UserListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class UserListViewModel : ViewModel() {

    private val repository = UserListRepository()


    private val userListLiveData = MutableLiveData<List<RemoteUser>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val users : LiveData<List<RemoteUser>>
        get() = userListLiveData

    val isLoading : LiveData<Boolean>
        get() = isLoadingLiveData

    fun searchUsers(query : String) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val users = repository.searchUsers(query = query)
                userListLiveData.postValue(users)
            }
            catch (t : Throwable) {
                userListLiveData.postValue(emptyList())
                Log.d("UserListFragment", "Error - ${t.message}")
            }
            finally {
                isLoadingLiveData.postValue(false)
            }
        }

    }


}