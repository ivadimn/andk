package com.example.permissions

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class PermissionNormalFragment : Fragment(R.layout.fragment_normal_permission) {

    private lateinit var imageView: ImageView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageView = requireView().findViewById(R.id.imageView)

        requireView().findViewById<Button>(R.id.loadImage).setOnClickListener {
            loadImageFromInternet()
        }


    }

    private fun loadImageFromInternet() {

        Glide.with(requireView())
                .load("https://avatarko.ru/img/avatar/28/avto_27389.jpg")
                .into(imageView)
    }
}