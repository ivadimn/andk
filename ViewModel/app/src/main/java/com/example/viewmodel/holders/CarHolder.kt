package com.example.viewmodel.holders

import android.view.View
import com.example.viewmodel.model.Entity
import kotlinx.android.synthetic.main.item_car.*


class CarHolder(
override val containerView : View,
onItemClick : (id : Long) -> Unit
) : BaseEntityHolder(containerView, onItemClick) {

    fun bind(entity : Entity.Car) {
        bindCommon(entity)
        powerTextView.text = "Мощность двигателя - ${entity.power} л/с"
        maxSpeedTextView.text = "Максимальная скорость - ${entity.maxSpeed} км/ч"
    }
}