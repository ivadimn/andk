package com.example.db.presentation.listusers

import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.db.App
import com.example.db.R
import com.example.db.database.model.User
import com.example.db.database.model.UserDetailRepository
import com.example.db.databinding.ItemUserBinding
import java.io.File

class UserViewHolder(
    private val binding : ItemUserBinding,
    val onDeleteClick : (User) -> Unit,
    val onItemClick : (Long) -> Unit

) : RecyclerView.ViewHolder(binding.root) {

    private val folder = App.context.getExternalFilesDir(UserDetailRepository.CHAT_EXTERNAL_DIR)

    fun bind(user : User) {

        binding.nameTextView.text = "${user.name} ${user.family}"
        binding.phoneTextView.text = user.phone
        binding.emailTextView.text = user.email

        val file = File(folder, user.photo)
        //binding.avatarImageView.setImageBitmap(bmp)
        Glide.with(binding.root)
            .load(BitmapFactory.decodeStream(file.inputStream()))
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.avatarImageView)

        binding.root.setOnClickListener {
            onItemClick(user.id)
        }

        binding.deleteImageView.setOnClickListener {
            onDeleteClick(user)
        }
    }
}