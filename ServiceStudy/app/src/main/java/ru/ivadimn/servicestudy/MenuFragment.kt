package ru.ivadimn.servicestudy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ivadimn.servicestudy.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private val binding : FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startedServiceButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToStartedServiceFragment()
            findNavController().navigate(action)
        }
    }
}