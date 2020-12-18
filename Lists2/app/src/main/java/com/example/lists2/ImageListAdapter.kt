package com.example.lists2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ImageListAdapter : RecyclerView.Adapter<ItemImageHolder>() {

    private lateinit var images : List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImageHolder {
        return ItemImageHolder(parent.inflate(R.layout.item_image))
    }

    override fun onBindViewHolder(holder: ItemImageHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun updateImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

}