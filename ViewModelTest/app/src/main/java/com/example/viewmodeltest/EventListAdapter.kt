package com.example.viewmodeltest

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.viewmodeltest.model.Event

class EventListAdapter : ListAdapter<Event, EventHolder>(EventDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(parent.inflate(R.layout.item_event))
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(currentList[position])
    }

}