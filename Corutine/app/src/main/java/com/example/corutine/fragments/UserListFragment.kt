package com.example.corutine.fragments

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
import com.example.corutine.R
import com.example.corutine.adapters.UserListAdapter

class UserListFragment : Fragment(R.layout.fragment_list_user) {

    private val viewModel : UserListViewModel by viewModels()

    private var usersAdapter : UserListAdapter? = null

    private lateinit var userList : RecyclerView
    private lateinit var queryEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initList()
        bindViewModel()
    }


    private fun initList() {
        usersAdapter = UserListAdapter()
        userList.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.users.observe(viewLifecycleOwner) {
            usersAdapter?.items = it
        }

    }

    private fun initViews() {
        userList = requireView().findViewById(R.id.userList)
        queryEditText = requireView().findViewById(R.id.queryEditText)
        searchButton = requireView().findViewById(R.id.searchButton)
        progressBar = requireView().findViewById(R.id.progressBar)

        searchButton.setOnClickListener {
            val query = queryEditText.text.toString()
            viewModel.searchUsers(query)
        }
    }

    private fun updateLoadingState(isLoading : Boolean) {
        progressBar.isVisible = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        usersAdapter = null
    }


}