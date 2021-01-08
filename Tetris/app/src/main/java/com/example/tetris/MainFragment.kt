package com.example.tetris

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var highScoreTextView: TextView
    private lateinit var newGameButton: Button
    private lateinit var resetScoreButton: Button
    private lateinit var exitButton: Button

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        highScoreTextView = requireView().findViewById(R.id.highScoreTextView)
        newGameButton = requireView().findViewById(R.id.newGameButton)
        resetScoreButton = requireView().findViewById(R.id.resetScoreButton)
        exitButton = requireView().findViewById(R.id.exitButton)

        exitButton.setOnClickListener {
            System.exit(0)
        }

        resetScoreButton.setOnClickListener {
            AppPreferences(requireContext()).clearHighScore()
            Snackbar.make(it, "Score successfully reset", Snackbar.LENGTH_SHORT).show()
        }

        newGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_gameFragment)
        }
    }
}