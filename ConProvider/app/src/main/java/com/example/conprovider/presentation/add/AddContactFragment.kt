package com.example.conprovider.presentation.add

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.conprovider.R
import com.example.conprovider.databinding.FragmentAddContactBinding
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class AddContactFragment : Fragment() {
    private var _binding : FragmentAddContactBinding? = null
    private val binding get() = _binding!!

    private val viewModel : AddContactViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        initViewModel()

    }

    private fun initViewModel() {
        binding.addContactButton.setOnClickListener { saveContactWithPermissionCheck() }
        viewModel.saveError.observe(viewLifecycleOwner) {toast(it)}
        viewModel.saveSuccess.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun initToolBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveContactWithPermissionCheck() {
        constructPermissionsRequest(
                Manifest.permission.WRITE_CONTACTS,
                onPermissionDenied = ::onContactPermissionDenied,
                onShowRationale = ::onContactPermissionShowRationale,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain) {
            viewModel.save(
                    binding.nameEditText.text.toString(),
                    binding.phoneEditText.text.toString()
            )
        }.launch()


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
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }



}