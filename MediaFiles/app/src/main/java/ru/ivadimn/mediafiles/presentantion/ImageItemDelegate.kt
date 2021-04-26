package ru.ivadimn.mediafiles.presentantion

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.ivadimn.mediafiles.Media
import ru.ivadimn.mediafiles.databinding.ItemImageBinding

class ImageItemDelegate(
    private val onDelete : (Long) -> Unit
) : AbsListItemAdapterDelegate<Media.Image, Media, ImageViewHolder>() {
    override fun isForViewType(item: Media, items: MutableList<Media>, position: Int): Boolean {
        return item is Media.Image
    }

    override fun onCreateViewHolder(parent: ViewGroup): ImageViewHolder {
        return ImageViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent,
        false), onDelete)
    }

    override fun onBindViewHolder(
        item: Media.Image,
        holder: ImageViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}