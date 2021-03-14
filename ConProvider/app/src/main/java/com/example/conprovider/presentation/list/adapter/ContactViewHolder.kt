package com.example.conprovider.presentation.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.conprovider.data.Contact
import com.example.conprovider.databinding.ItemContactBinding

class ContactViewHolder(
    val binding : ItemContactBinding,
    val onClick : (Contact) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact : Contact) {
        binding.root.setOnClickListener {
            onClick(contact)
        }

        binding.nameTextView.text = contact.name
        binding.phonesTextView.text = contact.phones.joinToString(separator = "\n")
    }
}