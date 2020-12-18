package com.example.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter (
    val onItemClick : (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_USER = 1
        private const val TYPE_DEVELOPER = 2
    }

    private var persons : List<Person> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_USER -> UserHolder(parent.inflate(R.layout.item_user), onItemClick)
            TYPE_DEVELOPER -> DeveloperHolder(parent.inflate(R.layout.item_developer), onItemClick)
            else -> error("Incorrect viewType = $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(persons[position]) {
            is Person.User -> TYPE_USER
            is Person.Developer -> TYPE_DEVELOPER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is UserHolder -> {
                val person = persons[position].let { it as? Person.User }
                        ?:  error("Person at position $position is not user")
                holder.bind(person)
            }
            is DeveloperHolder -> {
                val person = persons[position].let { it as? Person.Developer }
                        ?:  error("Person at position $position is not developer")
                holder.bind(person)
            }
            else -> error("Incorrect viewHolder = $holder")
        }
    }

    override fun getItemCount(): Int = persons.size

    fun updatePersons(newPersons : List<Person>) {
        persons = newPersons
    }
}