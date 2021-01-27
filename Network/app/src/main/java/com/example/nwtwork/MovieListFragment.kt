package com.example.nwtwork

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nwtwork.adapters.MovieListAdapter

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private var movieAdapter : MovieListAdapter? = null
    private val viewModel : MovieListViewModel by viewModels()

    private lateinit var movieNameEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieNameEditText = requireView().findViewById(R.id.movieNameEditText)
        searchButton = requireView().findViewById(R.id.searchButton)
        moviesList = requireView().findViewById(R.id.moviesList)
        progressBar = requireView().findViewById(R.id.progressBar)

        initList()
        bindViewModel()
    }


    private fun initList() {
        movieAdapter = MovieListAdapter()
        with(moviesList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        }
    }


    private fun bindViewModel() {
        searchButton.setOnClickListener {
            val queryText = movieNameEditText.text.toString()
            viewModel.search(queryText)
        }

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) {
            movieAdapter?.items = it
        }
    }

    private fun updateLoadingState(isLoading : Boolean) {
        moviesList.isVisible = isLoading.not()
        progressBar.isVisible = isLoading
        searchButton.isVisible = isLoading.not()
    }


}