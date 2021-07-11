package ru.ivadimn.di.adapters.user

import androidx.recyclerview.widget.DiffUtil
import ru.ivadimn.di.data.entities.User

class UserDiffUtilCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}