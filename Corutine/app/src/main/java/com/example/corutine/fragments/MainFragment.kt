package com.example.corutine.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.corutine.R

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var basicCoroutineButton: Button
    private lateinit var suspendButton: Button
    private lateinit var errorCancellingButton: Button
    private lateinit var androidCoroutineButton: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    fun initViews() {
        basicCoroutineButton = requireView().findViewById(R.id.basicCoroutineButton)
        suspendButton = requireView().findViewById(R.id.suspendButton)
        errorCancellingButton = requireView().findViewById(R.id.errorCancellingButton)
        androidCoroutineButton = requireView().findViewById(R.id.androidCoroutineButton)

        basicCoroutineButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToBasicCoroutineFragment2()
            findNavController().navigate(action)
        }

        suspendButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToUserListFragment()
            findNavController().navigate(action)
        }

        errorCancellingButton.setOnClickListener {
            val action =  MainFragmentDirections.actionMainFragmentToErrorCancelFragment()
            findNavController().navigate(action)
        }
    }

}