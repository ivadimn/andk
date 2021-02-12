package com.example.retrofit.adapters

import com.example.retrofit.model.RemoteUser
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UserListAdapter : AsyncListDifferDelegationAdapter<RemoteUser>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate())
    }
}