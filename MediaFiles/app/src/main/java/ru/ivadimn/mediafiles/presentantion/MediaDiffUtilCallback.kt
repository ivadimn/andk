package ru.ivadimn.mediafiles.presentantion

import androidx.recyclerview.widget.DiffUtil
import ru.ivadimn.mediafiles.Media

class MediaDiffUtilCallback : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return when{
            oldItem is Media.Image && newItem is Media.Image -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem == newItem
    }
}