package ru.ivadimn.MaterialDesign.adapters

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.ivadimn.material.databinding.ItemImageBinding
import ru.ivadimn.material.model.Image
import ru.ivadimn.material.ui.images.ImageDiffUtilCallback

class ImageListAdapter(
    private val onClick : (Image, ItemImageBinding) -> Unit
)
    : AsyncListDifferDelegationAdapter<Image>(ImageDiffUtilCallback())
{
        init {
            delegatesManager.addDelegate(ImageItemAdapterDelegate(onClick))
        }
}