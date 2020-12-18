package com.example.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
         private val onItemClicked : (position : Int) -> Unit
) : RecyclerView.Adapter<UserHolder>() {

    private var users :List<Person.User> = emptyList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(parent.inflate(R.layout.item_user), onItemClicked)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    fun updateUsers(newUsers : List<Person.User>) {
        users = newUsers
    }
}