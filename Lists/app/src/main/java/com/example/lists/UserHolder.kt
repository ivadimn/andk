package com.example.lists

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserHolder(
        view: View,
        onItemClick : (position : Int) -> Unit
): BasePersonHolder(view, onItemClick) {

    private val avatarImageView : ImageView = view.findViewById(R.id.avatarImageView)
    private val nameTextView : TextView = view.findViewById(R.id.nameTextView)
    private val ageTextView : TextView = view.findViewById(R.id.ageTextView)


    init {
        view.findViewById<TextView>(R.id.developerTextView).isVisible = false
    }

    fun bind(person: Person.User) {
        bindMainInfo(person.name, person.avatarLink, person.age)
    }
}