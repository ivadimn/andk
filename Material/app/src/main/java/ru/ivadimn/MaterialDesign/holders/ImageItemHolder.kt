package ru.ivadimn.MaterialDesign.holders

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import ru.ivadimn.MaterialDesign.App
import ru.ivadimn.material.databinding.ItemImageBinding
import ru.ivadimn.material.model.Image

class ImageItemHolder(
    private val binding : ItemImageBinding,
    private val onClick : (Image, ItemImageBinding) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image : Image) {

        val stream = ru.ivadimn.MaterialDesign.App.context.contentResolver.openInputStream(image.uri)
        binding.itemView.setImageBitmap(BitmapFactory.decodeStream(stream))

        binding.titleTextView.text = image.name
        binding.descriptionTextView.text = image.description
        binding.sizeTextView.text = "Size: ${image.size} b"
        binding.dimenTextView.text = "Resolution: ${image.width} x ${image.height}"

        binding.root.setOnClickListener {
            onClick(image, binding)
        }
    }
}