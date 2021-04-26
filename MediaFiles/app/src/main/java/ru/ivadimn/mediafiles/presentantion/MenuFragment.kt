package ru.ivadimn.mediafiles.presentantion

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.ivadimn.mediafiles.ViewBindingFragment
import ru.ivadimn.mediafiles.databinding.FragmentMenuBinding

class MenuFragment : ViewBindingFragment<FragmentMenuBinding>(
    FragmentMenuBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imagesButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToImagesFragment()
            findNavController().navigate(action)
        }
    }
}