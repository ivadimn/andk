package com.example.lists_2.adapters

import android.view.ViewGroup
import com.example.lists_2.Entity
import com.example.lists_2.R
import com.example.lists_2.holders.CarHolder
import com.example.lists_2.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class CarAdapterDelegate(
    val onItemClick : (position : Int) -> Unit
) :AbsListItemAdapterDelegate<Entity.Car, Entity, CarHolder>() {
    override fun isForViewType(item: Entity, items: MutableList<Entity>, position: Int): Boolean {
        return item is Entity.Car
    }

    override fun onCreateViewHolder(parent: ViewGroup): CarHolder {
        return CarHolder(parent.inflate(R.layout.item_car), onItemClick)
    }

    override fun onBindViewHolder(item: Entity.Car, holder: CarHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

}