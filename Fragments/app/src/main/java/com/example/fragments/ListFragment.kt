package com.example.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment

class ListFragment: Fragment(R.layout.fragment_list) {

    private val itemSelectListener  : ItemSelectListener ?
    get() = activity?.let { it as? ItemSelectListener }

    init {
        Log.d("LIST_FRAGMENT", "init activity = $activity")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LIST_FRAGMENT", "attach activity = $activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("LIST_FRAGMENT", "onActivityCreated")
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? Button }
            .forEach { button ->
                button.setOnClickListener {
                    onButtonClick(button.text.toString())
                }
            }
    }

    private fun onButtonClick(buttonText : String) {
        itemSelectListener?.onItemSelected(buttonText)
    }
}