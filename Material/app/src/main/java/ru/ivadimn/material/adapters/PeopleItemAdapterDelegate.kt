package ru.ivadimn.material.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.ivadimn.material.databinding.ItemContactBinding
import ru.ivadimn.material.holders.PeopleItemHolder
import ru.ivadimn.material.model.Contact

class PeopleItemAdapterDelegate
    : AbsListItemAdapterDelegate<Contact, Contact, PeopleItemHolder>(){
    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): PeopleItemHolder {
        return PeopleItemHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(
        item: Contact,
        holder: PeopleItemHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}