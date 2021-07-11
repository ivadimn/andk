package ru.ivadimn.di.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.ivadimn.di.data.entities.User
import ru.ivadimn.di.databinding.ItemUserBinding
import ru.ivadimn.di.holders.UserItemHolder

class UserItemAdapterDelegate(
    private val onCall : (String) -> Unit,
    private val onClick : (Long) -> Unit
)
    : AbsListItemAdapterDelegate<User, User, UserItemHolder>() {
    override fun isForViewType(item: User, items: MutableList<User>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): UserItemHolder {
        return UserItemHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), onCall, onClick)
    }

    override fun onBindViewHolder(item: User, holder: UserItemHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}