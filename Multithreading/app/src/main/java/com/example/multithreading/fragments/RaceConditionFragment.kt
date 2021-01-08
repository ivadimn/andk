package com.example.multithreading.fragments

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.multithreading.R
import com.example.multithreading.models.RaceConditionViewModel

class RaceConditionFragment :Fragment(R.layout.fragment_race_condition) {

    private lateinit var calcButton: Button
    private lateinit var calcTextView: TextView

    private val calcViewModel : RaceConditionViewModel by viewModels()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        calcButton = requireView().findViewById(R.id.calcButton)
        calcTextView = requireView().findViewById(R.id.calcTextView)

        observeCalcViewModel()

        calcButton.setOnClickListener {
            calcViewModel.calculate()
        }
    }

    fun observeCalcViewModel() {
        calcViewModel.number.observe(viewLifecycleOwner) {
            calcTextView.text ="${calcTextView.text}\n${it}"
        }
    }


}