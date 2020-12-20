package com.example.lists_2.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.lists_2.Entity
import com.example.lists_2.EntityDiffUtilCallback
import com.example.lists_2.R
import com.example.lists_2.holders.ActorHolder
import com.example.lists_2.holders.BaseEntityHolder
import com.example.lists_2.holders.CarHolder
import com.example.lists_2.inflate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class EntityListAdapter2(
    val onItemClick : (position : Int) -> Unit
)  : AsyncListDifferDelegationAdapter<Entity>(EntityDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ActorAdapterDelegate(onItemClick))
            .addDelegate(CarAdapterDelegate(onItemClick))
    }
}