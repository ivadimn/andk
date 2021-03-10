package com.example.conprovider.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.conprovider.data.Contact
import com.example.conprovider.databinding.ItemContactBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ContactListAdapterDelegate :
    AbsListItemAdapterDelegate<Contact, Contact, ContactViewHolder>() {
    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ContactViewHolder {
        return ContactViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(
        item: Contact,
        holder: ContactViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}