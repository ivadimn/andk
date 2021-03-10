package com.example.conprovider.presentation.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.conprovider.data.Contact

class ContactDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}