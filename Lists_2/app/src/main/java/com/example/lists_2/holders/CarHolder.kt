package com.example.lists_2.holders

import android.view.View
import com.example.lists_2.Entity
import kotlinx.android.synthetic.main.item_car.*

class CarHolder(
    override val containerView : View,
    onItemClick : (position : Int) -> Unit
) : BaseEntityHolder(containerView, onItemClick){

    fun bind(entity : Entity.Car) {
        bindCommon(entity)
        powerTextView.text = "Мощность двигателя - ${entity.power} л/с"
        maxSpeedTextView.text = "Максимальная скорость - ${entity.maxSpeed} км/ч"
    }
}

