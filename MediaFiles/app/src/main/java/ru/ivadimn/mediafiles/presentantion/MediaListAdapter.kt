package ru.ivadimn.mediafiles.presentantion

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.ivadimn.mediafiles.Media

class MediaListAdapter (
    private val onDelete : (Long) -> Unit
 ) : AsyncListDifferDelegationAdapter<Media>(MediaDiffUtilCallback()) {
     init {
         delegatesManager.addDelegate(ImageItemDelegate(onDelete))
     }
}