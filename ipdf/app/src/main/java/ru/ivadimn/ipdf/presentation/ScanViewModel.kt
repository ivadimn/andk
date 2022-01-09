package ru.ivadimn.ipdf.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.ivadimn.ipdf.data.ScanRepository

class ScanViewModel : ViewModel() {


    private val reposotory = ScanRepository()

    private val uriShareFlow = MutableSharedFlow<Uri>()
    val uriFlow: SharedFlow<Uri>
        get() = uriShareFlow


    fun getImageUri() {
        viewModelScope.launch {
            uriShareFlow.emit(reposotory.getImageUri())
        }
    }

    fun publicImage(imageUri : Uri) {
        viewModelScope.launch {
            reposotory.publicImage(imageUri)
        }
    }

    fun registerImage(img : Bitmap) {

    }

}