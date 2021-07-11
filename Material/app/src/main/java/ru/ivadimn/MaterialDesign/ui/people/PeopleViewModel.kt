package ru.ivadimn.MaterialDesign.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.ivadimn.material.model.Contact

class PeopleViewModel : ViewModel() {

    private val repository = PeopleRepository()
    private val peopleStateFlow = MutableStateFlow<List<Contact>>(emptyList())
    val peopleFlow : Flow<List<Contact>>
        get() = peopleStateFlow

    private val errorMessageStateFlow = MutableStateFlow<String>("")
    val errorMessageFlow : Flow<String>
        get() = errorMessageStateFlow

    private fun getPeople() {
        viewModelScope.launch {
            peopleStateFlow.emit(repository.getContacts())
        }
    }

    fun updatePermissionState(isGranted : Boolean) {
        if (isGranted)
            permissionGranted()
        else
            permissionDenied()
    }

    fun permissionGranted() {
        getPeople()
    }

    fun permissionDenied() {
        errorMessageStateFlow.value = "Access denied"
    }
}