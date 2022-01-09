package ru.ivadimn.ipdf.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.ivadimn.ipdf.databinding.FragmentMenuBinding
import ru.ivadimn.ipdf.utils.ViewBindingFragment

class MenuFragment : ViewBindingFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonScan.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToScanFragment()
            findNavController().navigate(action)
        }
    }
}