package com.example.conprovider.presentation.list

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.conprovider.databinding.FragmentContactListBinding
import com.example.conprovider.presentation.list.adapter.ContactListAdapter
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

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

        Handler(Looper.myLooper()!!).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = { viewModel.getAllContacts()}
            )
                .launch()
        }
    }

    private fun initViewModel() {
        viewModel.contactList.observe(viewLifecycleOwner) {
            contactAdapter?.items = it
        }
        viewModel.call.observe(viewLifecycleOwner, ::callToPhone)

        binding.contactAddButton.setOnClickListener {
            val action = ContactListFragmentDirections.actionContactListFragmentToAddContactFragment()
            findNavController().navigate(action)
        }
    }

    private fun initList() {
        contactAdapter = ContactListAdapter(viewModel::callToContact)
        binding.contactList.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun callToPhone(phone : String) {
        Intent(Intent.ACTION_DIAL)
                .apply { data = Uri.parse("tel:$phone") }
                .also { startActivity(it) }
    }

    private fun initToolbar() {
        binding.toolbar.title = "Contacts"
    }

    private fun onContactPermissionDenied() {
        toast("Доступ к контактам запрещён")
    }

    private fun onContactPermissionShowRationale(request : PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast("Разрешите доступ к контактам запрещён в настройках приложения...")
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        contactAdapter = null
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}