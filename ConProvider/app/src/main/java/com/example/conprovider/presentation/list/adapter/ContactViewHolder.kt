package com.example.conprovider.presentation.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.conprovider.data.Contact
import com.example.conprovider.databinding.ItemContactBinding

class ContactViewHolder(
    val binding : ItemContactBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact : Contact) {
        binding.nameTextView.text = contact.name
        binding.phonesTextView.text = contact.phones.joinToString(separator = "\n")
    }
}