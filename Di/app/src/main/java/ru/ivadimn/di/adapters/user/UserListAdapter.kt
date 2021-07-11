package ru.ivadimn.di.adapters.user

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.ivadimn.di.data.entities.User

class UserListAdapter(
    private val onCall : (String) -> Unit,
    private val onClick : (Long) -> Unit

) : AsyncListDifferDelegationAdapter<User>(UserDiffUtilCallback()){
    init {
        delegatesManager.addDelegate(UserItemAdapterDelegate(onCall, onClick))
    }

}