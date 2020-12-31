package com.example.viewmodel.fragments

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.viewmodel.R

class EntityDetailFragment : Fragment(R.layout.fragment_entity_detail) {

    private val args : EntityDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requireView().findViewById<TextView>(R.id.nameTextView)
            .text = args.entityId.toString()
    }
}