package ru.ivadimn.MaterialDesign.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.ivadimn.material.R
import ru.ivadimn.material.databinding.FragmentStubBinding
import ru.ivadimn.material.viewBinding

class StubFragment : Fragment(R.layout.fragment_stub) {

    private val binding : FragmentStubBinding by viewBinding(FragmentStubBinding::bind)

    private val args : StubFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()
        binding.captionTextView.text = args.caption
    }

    private fun initToolBar() {
        binding.toolbar.title = args.caption
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}