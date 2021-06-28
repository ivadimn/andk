package ru.ivadimn.material.ui.images

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.ivadimn.material.R
import ru.ivadimn.material.adapters.ImageListAdapter
import ru.ivadimn.material.databinding.FragmentListImageBinding
import ru.ivadimn.material.viewBinding

class ListImageFragment : Fragment(R.layout.fragment_list_image) {

    private val binding : FragmentListImageBinding by viewBinding(FragmentListImageBinding::bind)

    private var imageListAdapter : ImageListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun initList() {
        imageListAdapter = ImageListAdapter()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        imageListAdapter == null
    }
}