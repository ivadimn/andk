package ru.ivadimn.material.adapters

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.ivadimn.material.model.Image
import ru.ivadimn.material.model.ImageDiffUtilCallback

class ImageListAdapter
    : AsyncListDifferDelegationAdapter<Image>(ImageDiffUtilCallback())
{
        init {
            delegatesManager.addDelegate(ImageItemAdapterDelegate())
        }
}