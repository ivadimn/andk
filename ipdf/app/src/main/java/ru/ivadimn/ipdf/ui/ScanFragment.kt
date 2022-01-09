package ru.ivadimn.ipdf.ui

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import ru.ivadimn.ipdf.databinding.FragmentScanBinding
import ru.ivadimn.ipdf.presentation.ScanViewModel
import ru.ivadimn.ipdf.utils.ViewBindingFragment
import ru.ivadimn.ipdf.utils.haveQ
import ru.ivadimn.ipdf.utils.toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ScanFragment : ViewBindingFragment<FragmentScanBinding>(FragmentScanBinding::inflate) {

    private var permissionGranted = false
    private var photoUri : Uri? = null
    private var currentPhotoPath : String? = null


    private val viewModel : ScanViewModel by viewModels()

    //определяем запускалку интента на запрос разрешений
    private lateinit var requestPermissionLauncher : ActivityResultLauncher<Array<String>>
    private lateinit var takePictureLauncher : ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionLauncher()
        initTakePictureLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.buttonCaptureImage.setOnClickListener {
            if (hasPermission().not()) {
                requestPermissionLauncher.launch(PERMISSIONS.toTypedArray())
            }
            else {
               viewModel.getImageUri()
            }
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uriFlow.collect { imageUri ->
                photoUri = imageUri
                takePictureLauncher.launch(imageUri)
            }
        }
    }


    private fun initTakePictureLauncher() {
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture())
        { resultOk ->

            val bitmap = getBitmap(photoUri!!)
            binding.captureImage.setImageBitmap(bitmap)
            viewModel.publicImage(photoUri!!)
            toast("Image was captured")
        }
    }

    private fun initPermissionLauncher() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
            { permissionMap ->
                permissionGranted = permissionMap.values.all { it }
        }
    }

    private fun hasPermission() : Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getBitmap(uri : Uri) : Bitmap {
        val stream = requireContext().contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(stream)
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not()} ,
            Manifest.permission.CAMERA
        )
    }
}