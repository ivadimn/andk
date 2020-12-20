package com.example.lists_2.adapters

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import com.example.lists_2.ImageItem
import com.example.lists_2.ImageItemDiffUtilCallback
import com.example.lists_2.holders.ImageViewHolder
import com.example.lists_2.inflate

class ImageListAdapter(
    @LayoutRes val layoutRes : Int,
    val onItemClick : (position : Int) -> Unit
) : ListAdapter<ImageItem, ImageViewHolder>(ImageItemDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
       return ImageViewHolder(parent.inflate(layoutRes), onItemClick )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


}