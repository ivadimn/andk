package com.example.nwtwork.adapters

import android.view.ViewGroup
import com.example.nwtwork.MovieHolder
import com.example.nwtwork.R
import com.example.nwtwork.inflate
import com.example.nwtwork.model.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MovieAdapterDelegate :
    AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, MovieHolder>() {
    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean {
        return item is RemoteMovie
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(
        item: RemoteMovie,
        holder: MovieHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}