package ru.ivadimn.MaterialDesign.ui.imagedetail

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import ru.ivadimn.MaterialDesign.App
import ru.ivadimn.material.R
import ru.ivadimn.material.databinding.FragmentImageDetailBinding
import ru.ivadimn.material.viewBinding

class ImageDetailFragment : Fragment(R.layout.fragment_image_detail) {

    private val binding : FragmentImageDetailBinding by viewBinding(FragmentImageDetailBinding::bind)

    private val args : ImageDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = 500
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().getColor(R.color.design_default_color_surface))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        val stream = requireContext().contentResolver.openInputStream(args.image.uri)
        binding.itemView.setImageBitmap(BitmapFactory.decodeStream(stream))
        binding.descriptionTextView.text = args.image.longDescription
        binding.sizeTextView.text = "Size: ${args.image.size} b"
        binding.dimenTextView.text = "Resolution: ${args.image.width} x ${args.image.height}"
    }

    private fun initToolbar() {
        binding.toolbar.title = args.image.name
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}