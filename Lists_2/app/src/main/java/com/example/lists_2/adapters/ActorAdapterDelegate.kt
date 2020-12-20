package com.example.lists_2.adapters

import android.view.ViewGroup
import com.example.lists_2.Entity
import com.example.lists_2.R
import com.example.lists_2.holders.ActorHolder
import com.example.lists_2.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ActorAdapterDelegate(
    val onItemClick : (position : Int) -> Unit
) : AbsListItemAdapterDelegate<Entity.Actor, Entity, ActorHolder>() {
    override fun isForViewType(item: Entity, items: MutableList<Entity>, position: Int): Boolean {
       return item is Entity.Actor
    }

    override fun onCreateViewHolder(parent: ViewGroup): ActorHolder {
        return ActorHolder(parent.inflate(R.layout.item_actor), onItemClick)
    }

    override fun onBindViewHolder(
        item: Entity.Actor,
        holder: ActorHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

}