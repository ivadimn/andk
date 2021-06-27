package ru.ivadimn.ivnplayer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ivadimn.ivnplayer.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private val binding : FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playerButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToPlayerFragment()
            findNavController().navigate(action)
        }
    }
}