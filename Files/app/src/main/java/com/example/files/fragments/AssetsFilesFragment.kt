package com.example.files.fragments

import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.files.R
import com.example.files.databinding.FragmentAssetsFilesBinding

class AssetsFilesFragment : Fragment() {
    private var _binding : FragmentAssetsFilesBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssetsFilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playSoundButton.setOnClickListener {
            MediaPlayer.create(requireContext(), R.raw.clock_ticking).start()
        }

        resources.openRawResource(R.raw.emulator).use {
            val bytes = it.readBytes()
            val bmp  = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            binding.imageView.setImageBitmap(bmp)
        }

        val names = resources.assets.list("")
            .orEmpty()
            .joinToString()
        Log.d("AssetsFiles", names)

        try {
            val text = resources.assets.open("folder1/test_file.txt")
                .bufferedReader()
                .use {
                    it.readText()
                }
            Log.d("AssetsFiles", text)
        }
        catch (t: Throwable) {
            Log.d("AssetsFiles", t.message ?: "")
        }
    }
}