package com.example.db.presentation.listusers

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.db.R
import com.example.db.database.model.User
import com.example.db.databinding.ItemUserBinding

class UserViewHolder(
    val binding : ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user : User) {
        binding.nameTextView.text = "${user.name} ${user.family}"
        binding.phoneTextView.text = user.phone
        binding.emailTextView.text = user.family

        Glide.with(binding.root)
            .load(user.photo)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.avatarImageView)
    }
}