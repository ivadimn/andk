package com.example.viewmodeltest.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodeltest.EventListAdapter
import com.example.viewmodeltest.R
import com.google.android.material.textfield.TextInputLayout

class DataListFragment : Fragment(R.layout.fragment_list_data) {

    private val viewModel : DataViewModel by viewModels()
    //private var adapter : ArrayAdapter<String>? = null

    private lateinit var categoryTextView: AutoCompleteTextView
    private lateinit var typeInfoTextView: AutoCompleteTextView
    private lateinit var countryTextView: AutoCompleteTextView
    private lateinit var errorTextView: TextView
    private lateinit var repeatButton: Button
    private lateinit var searchButton: Button
    private lateinit var eventList: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var eventListAdapter : EventListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryTextView = requireView().findViewById(R.id.selectedCategory)
        typeInfoTextView = requireView().findViewById(R.id.selectedTypeInfo)
        countryTextView = requireView().findViewById(R.id.selectedCountry)
        searchButton = requireView().findViewById(R.id.searchButton)
        eventList = requireView().findViewById(R.id.eventList)
        progressBar = requireView().findViewById(R.id.progressBar)
        errorTextView = requireView().findViewById(R.id.errorTextView)
        repeatButton = requireView().findViewById(R.id.repeatButton)

        initList()
        initParametersLists()
        getParametersLists()
        initViewModel()

    }

    fun initList() {
        eventListAdapter = EventListAdapter()
        with(eventList) {
            adapter = eventListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun initParametersLists() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            val adapter = ArrayAdapter<String>(requireContext(), R.layout.item_event_type, categories)
            categoryTextView.setAdapter(adapter)
        }
        viewModel.typeInfoList.observe(viewLifecycleOwner) { typeList ->
            val adapter = ArrayAdapter<String>(requireContext(), R.layout.item_event_type,
                typeList)
            typeInfoTextView.setAdapter(adapter)
        }
        viewModel.countries.observe(viewLifecycleOwner) {countryList ->
            val adapter = ArrayAdapter<String>(requireContext(), R.layout.item_event_type,
                countryList)
            countryTextView.setAdapter(adapter)
        }
    }

    private fun getParametersLists() {
        viewModel.getCategories()
        viewModel.getTypeInfoList()
        viewModel.getCountries()
    }

    private fun initViewModel() {
        searchButton.setOnClickListener {
            val category = categoryTextView.text.toString()
            val type = typeInfoTextView.text.toString()
            val country = countryTextView.text.toString()
            Log.d("Eventfull Server", "Button Clicked")
            viewModel.getEvents(category, type, country)
        }

        repeatButton.setOnClickListener {
            viewModel.repeat()
        }

        viewModel.events.observe(viewLifecycleOwner) {
            eventListAdapter?.submitList(it)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {errorMessage ->
            errorTextView.text = errorMessage
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::updateViewState)
        viewModel.isContentError.observe(viewLifecycleOwner, ::updateViewStateContentError)
        viewModel.isNetworkError.observe(viewLifecycleOwner, ::updateViewStateNetworkError)
    }

    private fun updateViewState(isLoading : Boolean) {
        categoryTextView.isEnabled = !isLoading
        typeInfoTextView.isEnabled = !isLoading
        countryTextView.isEnabled = !isLoading
        searchButton.isEnabled = !isLoading
        progressBar.isVisible = isLoading
    }

    private fun updateViewStateContentError(isContentError : Boolean) {
        errorTextView.isVisible = isContentError
    }

    private fun updateViewStateNetworkError(isNetworkError : Boolean) {
        errorTextView.isVisible = isNetworkError
        categoryTextView.isEnabled = !isNetworkError
        typeInfoTextView.isEnabled = !isNetworkError
        countryTextView.isEnabled = !isNetworkError
        searchButton.isEnabled = !isNetworkError
        repeatButton.isVisible = isNetworkError
    }
}