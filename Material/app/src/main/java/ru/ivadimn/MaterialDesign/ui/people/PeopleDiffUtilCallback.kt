package ru.ivadimn.MaterialDesign.ui.people

import androidx.recyclerview.widget.DiffUtil
import ru.ivadimn.material.model.Contact

class PeopleDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}