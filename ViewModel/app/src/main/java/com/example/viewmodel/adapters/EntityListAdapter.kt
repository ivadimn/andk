package com.example.viewmodel.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.viewmodel.EntityDiffUtilCallback
import com.example.viewmodel.R
import com.example.viewmodel.holders.ActorHolder
import com.example.viewmodel.holders.BaseEntityHolder
import com.example.viewmodel.holders.CarHolder
import com.example.viewmodel.inflate
import com.example.viewmodel.model.Entity

class EntityListAdapter (
    val onItemClick : (id : Long) -> Unit
) : ListAdapter<Entity, BaseEntityHolder>(EntityDiffUtilCallback())  {

    companion object {
        private const val TYPE_ACTOR = 101
        private const val TYPE_CAR = 102
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseEntityHolder {
        return when(viewType) {
            TYPE_ACTOR -> ActorHolder(parent.inflate(R.layout.item_actor), onItemClick)
            TYPE_CAR -> CarHolder(parent.inflate(R.layout.item_car), onItemClick)
            else -> error("Unexpected view type = $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseEntityHolder, position: Int) {
        when(holder) {
            is ActorHolder -> {
                val entity = currentList[position].let { it as? Entity.Actor }
                    ?: error("Entity at position $position is not Actor")
                holder.bind(entity)
            }
            is CarHolder -> {
                val entity = currentList[position].let { it as? Entity.Car }
                    ?: error("Entity at position $position is not Car")
                holder.bind(entity)
            }
            else -> error("Unexpected view holder - $holder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is Entity.Actor -> TYPE_ACTOR
            is Entity.Car -> TYPE_CAR
        }
    }


}