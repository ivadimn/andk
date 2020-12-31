package com.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodel.model.ImageItem
import com.example.viewmodel.model.ImageListRepository

class ImageListViewModel : ViewModel() {

    private val imageListRepository = ImageListRepository()

    private val imageLiveData = MutableLiveData<List<ImageItem>>(imageListRepository.generateImages(20))

    private val showToastLiveData = SingleLiveEvent<Unit>()

    val images : LiveData<List<ImageItem>>
        get() = imageLiveData

    val showToast : SingleLiveEvent<Unit>
        get() = showToastLiveData

    fun addImage() {
        imageLiveData.postValue(imageListRepository.addImage(imageLiveData.value.orEmpty()))
        showToastLiveData.postValue(null)
    }

    fun deleteImage(position: Int) {
        imageLiveData.postValue(imageListRepository.deleteImage(position, imageLiveData.value.orEmpty()))
    }
}