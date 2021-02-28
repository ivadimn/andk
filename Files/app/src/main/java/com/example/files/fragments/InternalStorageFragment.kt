package com.example.files.fragments

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class InternalStorageFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cacheDir()
        fileDir()
    }



    private fun cacheDir() {
        lifecycleScope.launch(Dispatchers.IO) {
            val cacheDir = requireContext().cacheDir

            Log.d("InternalStorageFragment", cacheDir.absolutePath)

            val cacheFile = File(cacheDir, "test_cache.txt")
            try {
                cacheFile.outputStream().buffered().use {
                    it.write("Content in cache file".toByteArray())
                }
            }
            catch (t : Throwable) {
                Log.e("InternalStorageFragment", "error: ${t.message}")
            }
        }
    }

    private fun fileDir() {
        lifecycleScope.launch(Dispatchers.IO) {
            val fileDir = requireContext().filesDir

            Log.d("InternalStorageFragment", fileDir.absolutePath)

            val file = File(fileDir, "test_cache.txt")
            try {
                file.outputStream().buffered().use {
                    it.write("Content in file".toByteArray())
                }
            }
            catch (t : Throwable) {
                Log.e("InternalStorageFragment", "error: ${t.message}")
            }
        }
    }

}