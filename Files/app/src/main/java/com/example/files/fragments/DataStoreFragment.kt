package com.example.files.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.files.databinding.FragmentDataStoreBinding

class DataStoreFragment : Fragment() {

    private var _binding : FragmentDataStoreBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel :DataStoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveDataButton.setOnClickListener {
            viewModel.save(binding.inputDataEditText.text.toString())
        }
        viewModel.textLiveData.observe(viewLifecycleOwner) {
            binding.loadedDataTextView.text = it
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}