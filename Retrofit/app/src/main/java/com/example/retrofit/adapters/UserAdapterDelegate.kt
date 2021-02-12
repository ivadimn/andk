package com.example.retrofit.adapters

import android.view.ViewGroup
import com.example.retrofit.R
import com.example.retrofit.inflate
import com.example.retrofit.model.RemoteUser
import com.example.retrofit.viewholders.UserViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
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