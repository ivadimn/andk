package com.example.lists2

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.android.synthetic.main.item_image.view.*

class ItemImageHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(imageLink : String) {
        Glide.with(itemView)
            .load(imageLink)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageView)
    }
}