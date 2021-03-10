package com.example.conprovider.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.conprovider.databinding.FragmentContactListBinding
import com.example.conprovider.presentation.list.adapter.ContactListAdapter

class ContactListFragment : Fragment() {
    private var _binding : FragmentContactListBinding? = null
    val binding get() = _binding!!

    private val viewModel : ContactListViewModel by viewModels()

    private var contactAdapter : ContactListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initList()
        initViewModel()
        viewModel.getAllContacts()

    }

    private fun initViewModel() {
        viewModel.contactList.observe(viewLifecycleOwner) {
            contactAdapter?.items = it
        }
    }

    private fun initList() {
        contactAdapter = ContactListAdapter()
        binding.contactList.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = "Contacts"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        contactAdapter = null
    }

}