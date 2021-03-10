package com.example.conprovider.presentation.list.adapter

import com.example.conprovider.data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactListAdapter : AsyncListDifferDelegationAdapter<Contact>(ContactDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ContactListAdapterDelegate())
    }
}