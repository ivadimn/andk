package ru.ivadimn.mediafiles.presentantion

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ivadimn.mediafiles.ViewBindingFragment
import ru.ivadimn.mediafiles.databinding.FragmentListImageBinding
import ru.ivadimn.mediafiles.toast
import java.security.Permissions

class ImagesFragment : ViewBindingFragment<FragmentListImageBinding>(FragmentListImageBinding::inflate) {

    private val viewModel : ImageViewModel by viewModels()

    private var mediaListAdapter : MediaListAdapter? = null

    private lateinit var requestPermissionLauncher : ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionResultListener()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initViewModel()
        initViews()
        if (hasPermissions().not()) {
            requestPermissions()
        }
        else {
            viewModel.permissionGranted()
        }
    }

    private fun initList() {
        mediaListAdapter = MediaListAdapter(::delete)
        binding.itemList.apply {
            adapter = mediaListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            mediaListAdapter?.items = it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

    private fun initViews() {
        binding.mediaAddButton.setOnClickListener {
            toast("Abb button clicked")
        }
    }

    private fun delete(id: Long) {
        viewModel.deleteImage(id)
    }

    private fun hasPermissions() : Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionGrantedMap ->
           if (permissionGrantedMap.values.all { it }) {
               viewModel.permissionGranted()
           }
           else {
                viewModel.permissionDenied()
           }

        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(PERMISSIONS.toTypedArray())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaListAdapter = null
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { Build.VERSION.SDK_INT < Build.VERSION_CODES.Q }
        )
    }

}