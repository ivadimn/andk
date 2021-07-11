package ru.ivadimn.MaterialDesign.ui.images

import androidx.recyclerview.widget.DiffUtil
import ru.ivadimn.material.model.Image

class ImageDiffUtilCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}