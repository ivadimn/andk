package com.example.viewmodeltest.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodeltest.model.DataRepository
import com.example.viewmodeltest.model.Event
import com.example.viewmodeltest.network.Network
import okhttp3.Call

class DataViewModel : ViewModel() {

    private val repository = DataRepository()

    private var call : Call? = null

    private val categoriesLiveData = MutableLiveData<List<String>>()
    private val typeInfoListLiveData = MutableLiveData<List<String>>()
    private val countriesLiveData = MutableLiveData<List<String>>()
    private val eventsLiveData = MutableLiveData<List<Event>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val isNetworkErrorLiveData = MutableLiveData<Boolean>()
    private val isContentErrorLiveData = MutableLiveData<Boolean>()
    private val errorMessageLiveData = MutableLiveData<String>()



    val categories : LiveData<List<String>>
    get() = categoriesLiveData

    val typeInfoList : LiveData<List<String>>
    get() = typeInfoListLiveData

    val countries : LiveData<List<String>>
    get() = countriesLiveData

    val events : LiveData<List<Event>>
    get() = eventsLiveData

    val isLoading : LiveData<Boolean>
    get() = isLoadingLiveData

    val isNetworkError : LiveData<Boolean>
    get() = isNetworkErrorLiveData

    val isContentError : LiveData<Boolean>
    get() = isContentErrorLiveData

    val errorMessage : LiveData<String>
    get() = errorMessageLiveData

    private val callbackHandler = { events : List<Event>, isError : Boolean, errorMsg : String?  ->
        isLoadingLiveData.postValue(false)
        eventsLiveData.postValue(events)
        if (events.isEmpty()) {
            val message = errorMsg ?: "Вернулся пустой список попробуйте поискать с другими параметрами"
            errorMessageLiveData.postValue(message)
            if (isError)
                isNetworkErrorLiveData.postValue(true)
            else
                isContentErrorLiveData.postValue(true)
        }
    }


    fun getCategories() {
        repository.getCategoryList {
            categoriesLiveData.postValue(it)
        }
    }

    fun getTypeInfoList() {
        typeInfoListLiveData.postValue(repository.getTypeInfoList())
    }

    fun getCountries() {
        countriesLiveData.postValue(repository.getCountries())
    }

    fun getEvents(category : String, type : String, country : String) {
        isLoadingLiveData.postValue(true)
        isContentErrorLiveData.postValue(false)
        errorMessageLiveData.postValue("")
        Log.d("Eventfull Server", "view model getEvents")
        call = repository.getEvents(category, type, country, callbackHandler)
        call = null
    }

    fun repeat() {
        isLoadingLiveData.postValue(true)
        isNetworkErrorLiveData.postValue(false)
        errorMessageLiveData.postValue("")
        Log.d("Eventfull Server", "view model repeat call")
        call = repository.repeat(callbackHandler)
        call = null
    }

    override fun onCleared() {
        super.onCleared()
        call?.cancel()
    }

}