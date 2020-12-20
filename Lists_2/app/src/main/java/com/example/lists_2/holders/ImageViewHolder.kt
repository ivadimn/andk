package com.example.lists_2.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lists_2.ImageItem
import com.example.lists_2.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image_vertical.*

class ImageViewHolder (
    override val containerView: View,
    onItemClick : (position : Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        itemView.setOnClickListener { onItemClick(adapterPosition) }
    }

    fun bind(image : ImageItem) {
        Glide.with(itemView)
            .load(image.link)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageView)
    }
 }