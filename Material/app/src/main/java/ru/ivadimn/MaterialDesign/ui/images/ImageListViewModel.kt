package ru.ivadimn.MaterialDesign.ui.images

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.ivadimn.material.model.Image

class ImageListViewModel : ViewModel() {

    private val repository = ImageListRepository()

    private val imageListStateFlow = MutableStateFlow<List<Image>>(emptyList())
    val imageListFlow : Flow<List<Image>>
    get() = imageListStateFlow

    private val errorMessageStateFlow = MutableStateFlow<String>("")
    val errorMessageFlow : Flow<String>
        get() = errorMessageStateFlow

    fun getImageList() {
        viewModelScope.launch {
            imageListStateFlow.emit(repository.getImages())
        }
    }

    fun updatePermissionState(isGranted : Boolean) {
        if (isGranted)
            permissionGranted()
        else
            permissionDenied()
    }

    fun permissionGranted() {
        getImageList()
    }

    fun permissionDenied() {
        errorMessageStateFlow.value = "Access denied"
    }

    fun isShownSnackbar() : Boolean = repository.showSnackbar

    fun shownSnackbar() {
        repository.showSnackbar()
    }
}