package ru.ivadimn.di.ui.user

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.ivadimn.di.data.entities.User
import ru.ivadimn.di.interfaces.UserDetailRepository
import java.util.Optional.empty

class UserDetailViewModel(
    private val repository : UserDetailRepository
) : ViewModel() {

    private val userStateFlow = MutableStateFlow<User?>(null)
    val userFlow: StateFlow<User?>
        get() = userStateFlow

    private val uriShareFlow = MutableSharedFlow<Uri>()
    val uriFlow: SharedFlow<Uri>
        get() = uriShareFlow

    private val operationResultStateFlow = MutableStateFlow<Boolean>(false)
    val operationResultFlow: Flow<Boolean>
        get() = operationResultStateFlow

    private val errorMessageStateFlow = MutableStateFlow<String>("")
    val errorMessageFlow: Flow<String>
        get() = errorMessageStateFlow

    fun selectUser(userId: Long) {
        viewModelScope.launch {
            userStateFlow.emit(repository.select(userId))
        }
    }

    fun saveUser(data: Array<String>) {
        viewModelScope.launch {
            try {
                repository.save(data)
                operationResultStateFlow.emit(true)
            } catch (t: Throwable) {
                //operationResultStateFlow.emit(false)
                errorMessageStateFlow.emit("Error saving user - ${t.message}")
            }
        }
    }

    fun updateUser(data: Array<String>) {
        viewModelScope.launch {
            try {
                repository.update(data)
                operationResultStateFlow.emit(true)
            } catch (t: Throwable) {
                //operationResultStateFlow.emit(false)
                errorMessageStateFlow.emit("Error saving user - ${t.message}")
            }
        }
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch {
            try {
                repository.remove(userId)
                operationResultStateFlow.emit(true)
            } catch (t: Throwable) {
                //operationResultStateFlow.emit(false)
                errorMessageStateFlow.emit("Error saving user - ${t.message}")
            }
        }
    }

    fun registerPhoto(bmp: Bitmap) {
        viewModelScope.launch {
            repository.registerPhoto(bmp)
        }
    }

    fun getPhotoUri() {
        viewModelScope.launch {
            uriShareFlow.emit(repository.getPhotoUri())
        }
    }
}