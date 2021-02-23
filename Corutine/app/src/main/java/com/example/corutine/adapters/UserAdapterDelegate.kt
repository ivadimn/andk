package com.example.corutine.adapters

import android.view.ViewGroup
import com.example.corutine.R
import com.example.corutine.inflate
import com.example.corutine.model.RemoteUser
import com.example.corutine.viewholders.UserViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class UserAdapterDelegate : AbsListItemAdapterDelegate<RemoteUser, RemoteUser, UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup): UserViewHolder {
        return UserViewHolder(parent.inflate(R.layout.item_user))
    }

    override fun onBindViewHolder(
        item: RemoteUser,
        holder: UserViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    override fun isForViewType(
        item: RemoteUser,
        items: MutableList<RemoteUser>,
        position: Int
    ): Boolean {
        return true
    }

}