package com.example.lists_2

import androidx.recyclerview.widget.DiffUtil

class EntityDiffUtilCallback : DiffUtil.ItemCallback<Entity>() {
    override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean {
        return when {
            oldItem is Entity.Actor && newItem is Entity.Actor -> oldItem.id == newItem.id
            oldItem is Entity.Car && newItem is Entity.Car -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean {
        return oldItem == newItem
    }
}