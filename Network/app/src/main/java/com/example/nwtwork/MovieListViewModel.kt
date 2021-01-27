package com.example.nwtwork

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nwtwork.model.MovieRepository
import com.example.nwtwork.model.RemoteMovie

class MovieListViewModel : ViewModel()  {

    private val repository = MovieRepository()

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val movieList : LiveData<List<RemoteMovie>>
    get() = movieListLiveData

    val isLoading : LiveData<Boolean>
    get() = isLoadingLiveData

    fun search(text : String) {
        isLoadingLiveData.postValue(true)
        repository.searchMovie(text) {movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
        }
    }
}