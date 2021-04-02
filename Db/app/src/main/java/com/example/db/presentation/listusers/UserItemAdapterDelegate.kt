package com.example.db.presentation.listusers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.db.database.model.User
import com.example.db.databinding.ItemUserBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class UserItemAdapterDelegate : AbsListItemAdapterDelegate<User, User, UserViewHolder>() {
    override fun isForViewType(item: User, items: MutableList<User>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): UserViewHolder {
        return  UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(item: User, holder: UserViewHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}