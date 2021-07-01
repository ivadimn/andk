package ru.ivadimn.material.holders

import androidx.recyclerview.widget.RecyclerView
import ru.ivadimn.material.utils.DefaultAvatar
import ru.ivadimn.material.databinding.ItemContactBinding
import ru.ivadimn.material.model.Contact
import ru.ivadimn.material.model.ItemDetail

class PeopleItemHolder(
    private val binding: ItemContactBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact : Contact) {
        binding.nameTextView.text = contact.name
        if (contact.avatarUri != null) {
            binding.avatarImageView.setImageURI(contact.avatarUri)
        }
        else {
            binding.avatarImageView.setImageDrawable(DefaultAvatar(100F, 100F, contact.name.substring(0, 2)))
        }
        val phones = mutableListOf<String>()
        val emails = mutableListOf<String>()
        contact.info.details.forEach {
            when(it) {
                is ItemDetail.Phone -> phones.add(it.value)
                is ItemDetail.Email -> emails.add(it.value)
            }
        }
        binding.phoneTextView.text = "Phones: ${phones.joinToString(",")}"
        binding.emailTextView.text = "Emails: ${emails.joinToString(",")}"
    }
}