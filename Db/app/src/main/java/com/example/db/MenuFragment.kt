package com.example.db

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.db.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding : FragmentMenuBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usersButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToUserListFragment()
            findNavController().navigate(action)
        }

        binding.fkButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToMessageListFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}