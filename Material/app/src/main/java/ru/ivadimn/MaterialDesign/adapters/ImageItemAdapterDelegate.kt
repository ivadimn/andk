package ru.ivadimn.MaterialDesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.ivadimn.material.databinding.ItemImageBinding
import ru.ivadimn.material.holders.ImageItemHolder
import ru.ivadimn.material.model.Image

class ImageItemAdapterDelegate(
    private val onClick : (Image, ItemImageBinding) -> Unit
)
    : AbsListItemAdapterDelegate<Image, Image, ImageItemHolder>() {
    override fun isForViewType(item: Image, items: MutableList<Image>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ImageItemHolder {
        return ImageItemHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), onClick)
    }

    override fun onBindViewHolder(
        item: Image,
        holder: ImageItemHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}