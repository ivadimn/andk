package com.example.corutine.adapters

import com.example.corutine.model.RemoteUser
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UserListAdapter : AsyncListDifferDelegationAdapter<RemoteUser>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate())
    }
}