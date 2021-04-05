package com.example.db.presentation.adduser

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.db.R
import com.example.db.databinding.FragmentAddUserBinding
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer


class AddUserFragment : Fragment() {

    private var _binding : FragmentAddUserBinding? = null
    val binding get() = _binding!!

    private val args : AddUserFragmentArgs by navArgs()

    private val viewModel : AddUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initViewModel()
        registerActivityResult()
        if (args.userId > 0) {
            viewModel.getUserById(args.userId)
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.saveItem -> {
                    saveUser()
                    true
                }
                else -> false
            }
        }
    }

    private fun initViewModel() {
        viewModel.addOk.observe(viewLifecycleOwner) {
            Log.d("Add user", "Insert is ok - $it")
            findNavController().popBackStack()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            toast(it)
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.nameTextView.setText(it.name)
            binding.familyTextView.setText(it.family)
            binding.phoneTextView.setText(it.phone)
            binding.emailTextView.setText(it.email)
        }
    }

    private fun registerActivityResult() {

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Log.d("Add user", "Uri result - $uri")
            val bitmap = getBitmap(uri!!)
            binding.photoImageView.setImageBitmap(bitmap)
            viewModel.savePhoto(bitmap)
        }

        binding.photoImageView.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun saveUser() {
        when(args.userId) {
            0L -> {
                viewModel.insertUser(
                    binding.nameTextView.text.toString(), binding.familyTextView.text.toString(),
                    binding.phoneTextView.text.toString(), binding.emailTextView.text.toString()
                )
            }
            else ->  {
                viewModel.updateUser(
                    binding.nameTextView.text.toString(), binding.familyTextView.text.toString(),
                    binding.phoneTextView.text.toString(), binding.emailTextView.text.toString()
                )
            }
        }
    }

    private fun getBitmap(uri : Uri) : Bitmap {
        val stream = requireContext().contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(stream)
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PHOTO_REQUEST = 100
    }
}