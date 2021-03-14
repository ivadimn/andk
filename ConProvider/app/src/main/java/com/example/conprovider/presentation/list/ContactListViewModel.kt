package com.example.conprovider.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conprovider.data.Contact
import com.example.conprovider.data.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel : ViewModel() {

    private val repository = ContactRepository()

    private val contactListLiveData = MutableLiveData<List<Contact>>()
    private val callLiveData = MutableLiveData<String>()

    val contactList : LiveData<List<Contact>>
    get() = contactListLiveData

    val call : LiveData<String>
    get() = callLiveData

    fun getAllContacts() {
        viewModelScope.launch {
            contactListLiveData.postValue(repository.getAllContacts())
        }

    }

    fun callToContact(contact: Contact) {
        contact.phones.firstOrNull()?.let { callLiveData.postValue(it) }
    }

}