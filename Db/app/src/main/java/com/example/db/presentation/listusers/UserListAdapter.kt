package com.example.db.presentation.listusers

import com.example.db.database.model.User
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UserListAdapter :
    AsyncListDifferDelegationAdapter<User>(UserDiffUtilCallback()) {
        init {
            delegatesManager.addDelegate(UserItemAdapterDelegate())
        }
}