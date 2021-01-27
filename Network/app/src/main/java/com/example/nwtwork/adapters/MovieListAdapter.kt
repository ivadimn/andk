package com.example.nwtwork.adapters

import com.example.nwtwork.MovieDiffUtilCallback
import com.example.nwtwork.model.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieListAdapter : AsyncListDifferDelegationAdapter<RemoteMovie>(MovieDiffUtilCallback()) {
    init {
        delegatesManager.addDelegate(MovieAdapterDelegate())
    }
}