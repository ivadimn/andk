package ru.ivadimn.MaterialDesign.holders

import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.ivadimn.material.utils.DefaultAvatar
import ru.ivadimn.material.databinding.ItemContactBinding
import ru.ivadimn.material.model.Contact
import ru.ivadimn.material.model.Image
import ru.ivadimn.material.model.ItemDetail
import ru.ivadimn.material.ui.MainFragment

class PeopleItemHolder(
    private val binding: ItemContactBinding,
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

        binding.root.setOnLongClickListener {
            val card = (it as MaterialCardView)
            card.isChecked = !card.isChecked
            true
        }

    }
}