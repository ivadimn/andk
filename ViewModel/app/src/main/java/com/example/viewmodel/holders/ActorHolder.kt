package com.example.viewmodel.holders

import android.view.View
import com.example.viewmodel.model.Entity
import kotlinx.android.synthetic.main.item_actor.*

class ActorHolder(
    override val containerView: View,
    onItemClick : (id : Long) -> Unit
) : BaseEntityHolder(containerView, onItemClick) {


    fun bind(entity : Entity.Actor) {
        bindCommon(entity)

        birthdayTextView.text = entity.birthday
        filmsTextView.text = "Фильмы - ${entity.films}"
    }
}