package ru.ivadimn.material.ui.images

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ivadimn.material.R
import ru.ivadimn.material.adapters.ImageListAdapter
import ru.ivadimn.material.databinding.FragmentListImageBinding
import ru.ivadimn.material.haveQ
import ru.ivadimn.material.toast
import ru.ivadimn.material.viewBinding

class ImageListFragment : Fragment(R.layout.fragment_list_image) {

    private val binding : FragmentListImageBinding by viewBinding(FragmentListImageBinding::bind)
    private val viewModel : ImageListViewModel by viewModels()

    private var imageListAdapter : ImageListAdapter? = null

    private lateinit var requestPermissionLauncher : ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionRequestLauncher()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePermissionState(hasPermission())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initToolBar()
        initViewModel()
        if (!hasPermission()) {
            requestPermission()
        }
        showSnackBars()
    }

    private fun initList() {
        imageListAdapter = ImageListAdapter()
        binding.imageList.apply {
            adapter = imageListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(ImageItemDecoration(requireContext(), 8))
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imageListFlow.collect {
                imageListAdapter?.items = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessageFlow.collect {
                if (it.isNotEmpty())
                    toast(it)
            }
        }
    }

    private fun initToolBar() {
        binding.toolbar.title = "Image list"
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showSnackBars() {
        Snackbar.make(requireView(), R.string.snackMessageAction, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.repeat) {
                Snackbar.make(requireView(), R.string.list_updates, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColorStateList(requireContext(), R.color.backgroundColor)!!.defaultColor)
                    .show()
            }
            .setBackgroundTint(ContextCompat.getColorStateList(requireContext(), R.color.backgroundColor)!!.defaultColor)
            .setActionTextColor(ContextCompat.getColorStateList(requireContext(), R.color.yellow)!!.defaultColor)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageListAdapter == null
    }

    private fun hasPermission() : Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initPermissionRequestLauncher() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap : Map<String, Boolean> ->
            if (permissionsMap.values.all { it }) {
                viewModel.permissionGranted()
            }
            else {
                viewModel.permissionDenied()
            }
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(PERMISSIONS.toTypedArray())
    }

    companion object {
            private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() }
        )
    }
}