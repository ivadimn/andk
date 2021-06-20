package com.example.workstudy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.workstudy.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private val binding : FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        binding.downloadButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToDownloadFragment()
            findNavController().navigate(action)
        }
    }
}