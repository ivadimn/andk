package com.example.files.fragments

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.files.databinding.FragmentExternalStorageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ExternalStorageFragment : Fragment() {

    private var _binding : FragmentExternalStorageBinding? = null
    val binding
    get() = _binding!!


    private val viewModel : ExternalStorageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExternalStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        getFile()
    }

    private fun initViewModel() {
        viewModel.isLoaded.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "File was loaded", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "Error loading file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun externalStorage() {
        lifecycleScope.launch(Dispatchers.IO) {

            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
                return@launch

            val fileDir = requireContext().getExternalFilesDir("testFolder")

            Log.d("ExternalStorageFragment", fileDir?.absolutePath ?: "unknown")

            val file = File(fileDir, "test_file.txt")
            try {
                file.outputStream().buffered().use {
                    it.write("Content in external storage file".toByteArray())
                }
            }
            catch (t : Throwable) {
                Log.e("ExternalStorageFragment", "error: ${t.message}")
            }
        }
    }

    private fun getFile() {
        viewModel.getFile("https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}