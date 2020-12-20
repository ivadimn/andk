package com.example.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class PersonListAdapter(
    val onItemClick : (position: Int) -> Unit
) : ListAdapter<Person, BasePersonHolder>(PersonDiffUtilCallback()) {

    companion object {
        private const val TYPE_USER = 1
        private const val TYPE_DEVELOPER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasePersonHolder {
        return when(viewType) {
            TYPE_USER -> UserHolder(parent.inflate(R.layout.item_user), onItemClick)
            TYPE_DEVELOPER -> DeveloperHolder(parent.inflate(R.layout.item_developer), onItemClick)
            else -> error("Unexpected view type - $viewType")
        }
    }

    override fun onBindViewHolder(holder: BasePersonHolder, position: Int) {
        when(holder) {
            is UserHolder -> {
                val person = currentList[position].let { it as Person.User }
                holder.bind(person)
            }
            is DeveloperHolder -> {
                val person = currentList[position].let {it as Person.Developer}
                holder.bind(person)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is Person.User -> TYPE_USER
            is Person.Developer -> TYPE_DEVELOPER
        }
    }

    fun updatePersons(newPersons : List<Person>) {
        submitList(newPersons)
    }

}