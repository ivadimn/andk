package com.example.lists_2.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lists_2.Entity
import com.example.lists_2.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_car.*

abstract class BaseEntityHolder(
    override val containerView: View,
    onItemClick : (position : Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        itemView.setOnClickListener { onItemClick(adapterPosition) }
    }

    fun bindCommon(entity: Entity) {

        nameTextView.text = entity.name
        countryTextView.text = entity.country

        Glide.with(itemView)
            .load(entity.imageLink)
            .placeholder(R.drawable.ic_placeholder)
            .into(entityImageView)
    }

}