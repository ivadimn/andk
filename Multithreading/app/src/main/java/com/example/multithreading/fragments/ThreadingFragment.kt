package com.example.multithreading.fragments

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.multithreading.R
import com.example.multithreading.models.ThreadingViewModel

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    private lateinit var requestButton: Button
    private lateinit var timeTextView: TextView
    private lateinit var dataTextView: TextView

    private val dataViewModel : ThreadingViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requestButton = requireView().findViewById(R.id.requestButton)
        timeTextView = requireView().findViewById(R.id.timeTextView)
        dataTextView = requireView().findViewById(R.id.dataTextView)
        observeDataViewModel()

        requestButton.setOnClickListener {
            dataViewModel.getData()
        }

    }


    private fun observeDataViewModel() {
        dataViewModel.time.observe(viewLifecycleOwner) {
            timeTextView.text = it.toString()
        }
        dataViewModel.data.observe(viewLifecycleOwner) {
            dataTextView.text = it
        }
    }
}