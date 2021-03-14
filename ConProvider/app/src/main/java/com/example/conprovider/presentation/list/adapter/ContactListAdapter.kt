package com.example.conprovider.presentation.list.adapter

import com.example.conprovider.data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactListAdapter(
        val onClick : (Contact) -> Unit
) : AsyncListDifferDelegationAdapter<Contact>(ContactDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ContactListAdapterDelegate(onClick))
    }
}