package com.example.corutine.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.corutine.model.RemoteUser

class UserDiffUtilCallback : DiffUtil.ItemCallback<RemoteUser>() {
    override fun areItemsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
        return oldItem == newItem
    }
}