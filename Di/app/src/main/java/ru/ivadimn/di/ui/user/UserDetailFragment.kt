package ru.ivadimn.di.ui.user

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ivadimn.di.*
import ru.ivadimn.di.data.entities.User
import ru.ivadimn.di.databinding.FragmentDetailUserBinding
import ru.ivadimn.di.utils.DefaultAvatar
import java.io.File


class UserDetailFragment : Fragment(R.layout.fragment_detail_user) {

    private val binding : FragmentDetailUserBinding by viewBinding(FragmentDetailUserBinding::bind)

    private val viewModel : UserDetailViewModel by viewModels { ViewModelFactory() }

    private val args : UserDetailFragmentArgs by navArgs()
    private val folder = App.context.getExternalFilesDir(App.MEDIA_EXTERNAL_DIR)

    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>

    private var photoUri : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRequestPermissions()
        initTakePictureLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initToolBar()
        initViews()
        registerActivityResult()
        if (args.userId > 0)
            viewModel.selectUser(args.userId)
    }

    private fun initViewModel() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userFlow.collect { user ->
                Log.d("DINJ", "collect user - $user")
                user?.let { showUserDetail(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.operationResultFlow.collect {
                if (it)
                    findNavController().popBackStack()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessageFlow.collect {
                toast(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uriFlow.collect {
                Log.d("DINJ", "getted uri - $it")
                photoUri = it
                takePictureLauncher.launch(photoUri)
            }
        }
    }

    private fun initToolBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.saveMenuItem -> {
                    saveUser()
                    true
                }
                R.id.deleteMenuItem -> {
                    if (args.userId > 0)
                        viewModel.deleteUser(args.userId)
                    true
                }
                else -> false
            }
        }
    }

    private fun initViews() {
        binding.cameraImageView.setOnClickListener {
            if (hasPermissions())
                viewModel.getPhotoUri()
            else
                permissionsLauncher.launch(PERMISSIONS.toTypedArray())
        }
    }

    private fun showUserDetail(user : User) {

        if (user.photo.isNotEmpty()) {
            binding.photoImageView.setImageBitmap(
                BitmapFactory.decodeStream(File(folder, user.photo).inputStream())
            )
        }
        else {
            val width = binding.photoImageView.width
            val height = binding.photoImageView.height
            binding.photoImageView.setImageDrawable(DefaultAvatar(width.toFloat(), height.toFloat() , "${user.name[0]}${user.family[0]}"))
        }
        binding.nameTextView.setText(user.name)
        binding.familyTextView.setText(user.family)
        binding.phoneTextView.setText(user.phone)
        binding.emailTextView.setText(user.email)
    }

    private fun saveUser() {
        when(args.userId) {
            0L -> viewModel.saveUser(packData())
            else -> viewModel.updateUser(packData())
        }
    }

    private fun packData() : Array<String> {
        var data = mutableListOf(
            binding.nameTextView.text.toString(),
            binding.familyTextView.text.toString(),
            binding.phoneTextView.text.toString(),
            binding.emailTextView.text.toString()
        )

        return data.toTypedArray()
    }

    private fun registerActivityResult() {

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            val bitmap = getBitmap(uri)
            binding.photoImageView.setImageBitmap(bitmap)
            viewModel.registerPhoto(bitmap)
        }

        binding.photoImageView.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun getBitmap(uri : Uri) : Bitmap {
        val stream = requireContext().contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(stream)
    }



    private fun initRequestPermissions() {
        permissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions() )  { mapOfPermissions ->
                if (mapOfPermissions.values.all { it }) {
                    viewModel.getPhotoUri()
                }
                else {
                    toast("Camera permission denied")
                }
        }
    }

    private fun hasPermissions() : Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(requireContext(),
                it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initTakePictureLauncher() {
        takePictureLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture()) { resultOk ->
                if (resultOk) {

                    val bitmap = getBitmap(photoUri!!)
                    Log.d("DINJ", "Result ok - ${bitmap.width}, ${bitmap.height}")
                    binding.photoImageView.setImageBitmap(bitmap)
                    viewModel.registerPhoto(bitmap)
                }
            }

    }



    companion object {
        const val REQUEST_IMAGE_CAPTURE = 999

        val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() },
            Manifest.permission.CAMERA )
    }
}