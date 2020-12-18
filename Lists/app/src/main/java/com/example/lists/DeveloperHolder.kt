package com.example.lists

import android.view.View
import android.widget.TextView

class DeveloperHolder(
        view : View,
        onItemClick : (position : Int) -> Unit
) :BasePersonHolder(view, onItemClick) {

    private val programmingLanguageTextView : TextView =
            view.findViewById(R.id.programmingLanguageTextView)

    fun bind(person : Person.Developer) {
        bindMainInfo(person.name, person.avatarLink, person.age)
        programmingLanguageTextView.text =
                "Язык программирования ${person.programmingLanguage}"
    }
}