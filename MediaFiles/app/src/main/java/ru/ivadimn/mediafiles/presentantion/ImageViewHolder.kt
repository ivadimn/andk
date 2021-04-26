package ru.ivadimn.mediafiles.presentantion

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ivadimn.mediafiles.Media
import ru.ivadimn.mediafiles.R
import ru.ivadimn.mediafiles.databinding.ItemImageBinding

class ImageViewHolder(
    private val binding : ItemImageBinding,
    private val onDelete : (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image : Media.Image) {
        //binding.fileTextView.ellipsize = TextUtils.TruncateAt.END
        binding.fileTextView.text = image.name
        binding.sizeTextView.text = "${image.size}"

        Glide.with(binding.root)
            .load(image.uri)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.itemView)
    }
}