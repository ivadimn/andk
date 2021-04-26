package ru.ivadimn.mediafiles.presentantion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ivadimn.mediafiles.Media
import ru.ivadimn.mediafiles.SingleLiveEvent

class ImageViewModel : ViewModel() {

    private val repository = ImageRepository()

    private val imageListLiveData = MutableLiveData<List<Media>>()
    val imageList : LiveData<List<Media>>
        get() = imageListLiveData

    private val errorLiveData = SingleLiveEvent<String>()
    val error : SingleLiveEvent<String>
        get() = errorLiveData


    fun permissionGranted() {
        viewModelScope.launch {
            try {
                imageListLiveData.postValue(repository.getImages())
            }
            catch (t : Throwable) {
                Log.d("Fragment", "Error get images = ${t.message}")
                errorLiveData.postValue(t.message ?: "непонятная ошибка" )
                imageListLiveData.postValue(emptyList())
            }
        }
    }

    fun permissionDenied() {
        errorLiveData.postValue("Permission denied")
    }

    private fun getImages() {
        viewModelScope.launch {
            try {
                imageListLiveData.postValue(repository.getImages())
            }
            catch (t : Throwable) {
                Log.d("Fragment", "Error get images = ${t.message}")
                errorLiveData.postValue(t.message ?: "непонятная ошибка" )
                imageListLiveData.postValue(emptyList())
            }
        }
    }

    fun saveImage(name : String, url : String) {
        viewModelScope.launch {
            try {
                repository.saveImage(name, url)
                imageListLiveData.postValue(repository.getImages())
            }
            catch (t : Throwable) {
                Log.d("Fragment", "Error save image = ${t.message}")
                errorLiveData.postValue(t.message)
            }
        }
    }



    fun deleteImage(id : Long) {
        viewModelScope.launch {
            try {
                repository.deleteImage(id)
                imageListLiveData.postValue(repository.getImages())
            }
            catch (t : Throwable) {
                Log.d("Fragment", "Error delete image = ${t.message}")
                errorLiveData.postValue(t.message)
            }
        }
    }

}