package com.example.viewmodeltest

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewmodeltest.model.Event
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event.*

class EventHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(event : Event) {
        titleTextView.text = event.title
        countryTextView.text = event.country
        dateTextView.text = event.date

        Glide.with(itemView)
            .load(event.image)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageView)
    }
}