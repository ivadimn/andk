package com.example.lists_2.holders

import android.view.View
import com.example.lists_2.Entity
import kotlinx.android.synthetic.main.item_actor.*

class ActorHolder(
    override val containerView: View,
    onItemClick : (position :Int) -> Unit
) : BaseEntityHolder(containerView, onItemClick) {


    fun bind(entity : Entity.Actor) {
        bindCommon(entity)

        birthdayTextView.text = entity.birthday
        filmsTextView.text = "Фильмы - ${entity.films}"
    }
}