package com.example.nwtwork

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nwtwork.model.RemoteMovie
import kotlinx.android.extensions.LayoutContainer

class MovieHolder(
       override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    val movieNameTextView = itemView.findViewById<TextView>(R.id.movieNameTextView)
    val movieYearTextView = itemView.findViewById<TextView>(R.id.movieYearTextView)
    val movieDurationTextView = itemView.findViewById<TextView>(R.id.movieDurationTextView)

    fun bind(movie : RemoteMovie) {
        movieNameTextView.text = movie.title
        movieYearTextView.text = "Год выпуска - ${movie.year}"
        movieDurationTextView.text = "Продолжительность - ${movie.duration}"
    }

}