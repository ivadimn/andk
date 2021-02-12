package com.example.retrofit.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.R
import com.example.retrofit.model.RemoteUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*

class UserViewHolder(
    override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer  {

    fun bind(user : RemoteUser) {
        userNameTextView.text = user.username

        Glide.with(itemView)
            .load(user.avatar)
            .placeholder(R.drawable.ic_placeholder)
            .into(userImageView)

    }
}