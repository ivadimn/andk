package com.example.nwtwork

import androidx.recyclerview.widget.DiffUtil
import com.example.nwtwork.model.RemoteMovie

class MovieDiffUtilCallback : DiffUtil.ItemCallback<RemoteMovie>() {
    override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
        return oldItem == newItem
    }
}