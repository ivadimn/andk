package com.example.db.presentation.adduser

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.db.SingleLiveEvent
import com.example.db.database.model.user.User
import com.example.db.database.model.user.UserDetailRepository
import kotlinx.coroutines.launch

class AddUserViewModel : ViewModel() {

    private val repository = UserDetailRepository()

    private val addOkLiveData = MutableLiveData<Int>()
    val addOk : LiveData<Int>
    get() = addOkLiveData

    private val errorLiveData = SingleLiveEvent<String>()
    val error : SingleLiveEvent<String>
    get() = errorLiveData

    private val userLiveData = SingleLiveEvent<User>()
    val user : SingleLiveEvent<User>
    get() = userLiveData

    fun insertUser(name : String, family : String, phone : String, email : String) {
        viewModelScope.launch {
            try {
                val count = repository.insertUser(name, family, phone, email)
                addOkLiveData.postValue(count)
            } catch (t: Throwable) {
                errorLiveData.postValue(t.message)
            }
        }
    }

    fun getUserById(userId : Long) {
        viewModelScope.launch {
            try {
                userLiveData.postValue(repository.getUserById(userId))
            }
            catch(t: Throwable) {
                errorLiveData.postValue(t.message)
            }
        }
    }

    fun updateUser(name : String, family : String, phone : String, email : String) {
        viewModelScope.launch {
            try {
                val count = repository.updateUser(name, family, phone, email)
                addOkLiveData.postValue(count)
            } catch (t: Throwable) {
                errorLiveData.postValue(t.message)
            }
        }
    }

    fun savePhoto(bmp : Bitmap) {
        viewModelScope.launch {
            repository.registerPhoto(bmp)
        }
    }
}