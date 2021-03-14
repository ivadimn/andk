package com.example.conprovider.presentation.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conprovider.SingleLiveEvent
import com.example.conprovider.data.ContactRepository
import com.example.conprovider.data.IncorrectInformationException
import kotlinx.coroutines.launch

class AddContactViewModel : ViewModel() {
    private val repository = ContactRepository()

    private val saveErrorLiveData = SingleLiveEvent<String>()
    private val saveSuccessLiveData = SingleLiveEvent<String>()

    val saveError : LiveData<String>
    get() = saveErrorLiveData

    val saveSuccess : LiveData<String>
    get() = saveSuccessLiveData


    fun save(name : String, phone : String) {
        viewModelScope.launch {
            try {
                repository.saveContact(name, phone)
                saveSuccessLiveData.postValue("Ok")
            }
            catch (t : Throwable) {
                Log.d("ContactVieModel", "Contact add error", t)
                showError(t)
            }
        }
    }

    private fun showError(t : Throwable) {
        saveErrorLiveData.postValue(
                when(t) {
                    is IncorrectInformationException -> "Неправильно заполнена форма ..."
                    else -> t.message
                }
        )
    }
}