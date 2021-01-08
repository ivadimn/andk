package com.example.multithreading.fragments

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.multithreading.R

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var threadingButton: Button
    private lateinit var raceConditionButton: Button
    private lateinit var deadlockButton: Button
    private lateinit var handlerButton: Button

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        threadingButton = requireView().findViewById(R.id.threadsButton)
        raceConditionButton = requireView().findViewById(R.id.raceConditionButton)
        deadlockButton = requireView().findViewById(R.id.deadlockButton)
        handlerButton = requireView().findViewById(R.id.handlerButton)

        threadingButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToThreadingFragment()
            findNavController().navigate(action)
        }

        raceConditionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_raceConditionFragment)
        }
        deadlockButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_deadlockFragment)
        }

        handlerButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_handlerFragment)
        }
    }

}