package com.example.db.presentation.listusers

import com.example.db.database.model.user.User
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UserListAdapter (
        private val onDeleteClick : (User) -> Unit,
        val onItemClick : (Long) -> Unit
) :
    AsyncListDifferDelegationAdapter<User>(UserDiffUtilCallback()) {
        init {
            delegatesManager.addDelegate(UserItemAdapterDelegate(onDeleteClick, onItemClick))
        }
}