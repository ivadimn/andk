package ru.ivadimn.flowstudy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ivadimn.flowstudy.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding : FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.flowBasicButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToFlowBasicFragment()
            findNavController().navigate(action)
        }

        binding.flowOperatorsButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToFlowOperatorsFragment()
            findNavController().navigate(action)
        }
    }
}