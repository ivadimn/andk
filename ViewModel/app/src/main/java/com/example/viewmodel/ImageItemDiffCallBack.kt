package com.example.viewmodel

import androidx.recyclerview.widget.DiffUtil
import com.example.viewmodel.model.ImageItem

class ImageItemDiffUtilCallback : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }
}