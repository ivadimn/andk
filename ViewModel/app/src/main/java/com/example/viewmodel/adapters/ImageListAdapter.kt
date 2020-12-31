package com.example.viewmodel.adapters

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import com.example.viewmodel.ImageItemDiffUtilCallback
import com.example.viewmodel.R
import com.example.viewmodel.holders.ImageViewHolder
import com.example.viewmodel.inflate
import com.example.viewmodel.model.ImageItem

class ImageListAdapter(
   val onItemClick : (position : Int) -> Unit
) : ListAdapter<ImageItem, ImageViewHolder>(ImageItemDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(parent.inflate(R.layout.item_image_grid), onItemClick )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


}