package com.example.nwtwork

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nwtwork.model.MovieRepository
import com.example.nwtwork.model.RemoteMovie
import okhttp3.Call

class MovieListViewModel : ViewModel()  {

    private val repository = MovieRepository()

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private var currentCall : Call? = null

    val movieList : LiveData<List<RemoteMovie>>
    get() = movieListLiveData

    val isLoading : LiveData<Boolean>
    get() = isLoadingLiveData

    fun search(text : String) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(text) {movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
        }
        currentCall = null
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}