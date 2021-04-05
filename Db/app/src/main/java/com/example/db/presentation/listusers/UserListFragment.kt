package com.example.db.presentation.listusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.db.database.model.user.User
import com.example.db.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {
    private var _binding : FragmentUserListBinding? = null
    val binding get() = _binding!!

    private val viewModel : UserListViewModel by viewModels()

    private var userListAdapter : UserListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initViewModel()
        initViews()
        viewModel.getAllUsers()

    }

    private fun initViews() {
        binding.userAddButton.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToAddUserFragment(0)
            findNavController().navigate(action)
        }
    }

    private fun initList() {
        userListAdapter = UserListAdapter(::removeUser, ::updateUser)
        binding.userList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun removeUser(user : User) {
        viewModel.removeUser(user)
    }

    private fun updateUser(userId : Long) {
        val action = UserListFragmentDirections.actionUserListFragmentToAddUserFragment(userId)
        findNavController().navigate(action)
    }

    private fun initViewModel() {
        viewModel.userList.observe(viewLifecycleOwner) {
            userListAdapter?.items = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        userListAdapter = null
    }


}