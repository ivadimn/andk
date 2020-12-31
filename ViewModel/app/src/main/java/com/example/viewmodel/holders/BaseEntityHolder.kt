package com.example.viewmodel.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewmodel.R
import com.example.viewmodel.model.Entity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_actor.*

abstract class BaseEntityHolder(
    override val containerView: View,
    val onItemClick : (id : Long) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {



    fun bindCommon(entity: Entity) {

        itemView.setOnClickListener { onItemClick(entity.id) }

        nameTextView.text = entity.name
        countryTextView.text = entity.country

        Glide.with(itemView)
            .load(entity.imageLink)
            .placeholder(R.drawable.ic_placeholder)
            .into(entityImageView)
    }

}