package ru.ivadimn.MaterialDesign.adapters

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.ivadimn.material.model.Contact
import ru.ivadimn.material.ui.people.PeopleDiffUtilCallback

class PeopleAdapter
    : AsyncListDifferDelegationAdapter<Contact>(PeopleDiffUtilCallback()) {

        init {
            delegatesManager.addDelegate(PeopleItemAdapterDelegate())
        }
}