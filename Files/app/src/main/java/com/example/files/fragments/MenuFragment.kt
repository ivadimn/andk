package com.example.files.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.files.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.assetsFilesButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToAssetsFilesFragment()
            findNavController().navigate(action)
        }
        binding.internalStorageButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToInternalStorageFragment()
            findNavController().navigate(action)
        }

        binding.externalStorageButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToExternalStorageFragment()
            findNavController().navigate(action)
        }

        binding.sharedPrefsButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToSharedPreferencesFragment()
            findNavController().navigate(action)
        }
        binding.dataStoreButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToDataStoreFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}