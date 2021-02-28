package com.example.files.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.files.databinding.FragmentSharedPreferencesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SharedPreferencesFragment : Fragment() {

   companion object {
       private const val SHARED_PREFS_NAME = "skillbox_shared_prefs"
       private const val KEY = "saved_text"
   }

    private var _binding : FragmentSharedPreferencesBinding? = null
    private val binding
    get() = _binding!!

    private val sharedPrefs : SharedPreferences by lazy {
        requireContext()
            .getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val sharedPrefsListener = SharedPreferences.OnSharedPreferenceChangeListener {
            sharedPreferences, key ->
        lifecycleScope.launch { updateLoadedText()}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSharedPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            sharedPrefs.registerOnSharedPreferenceChangeListener(sharedPrefsListener)
        }

        binding.saveButton.setOnClickListener {
            val textToSave = binding.inputEditText.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                sharedPrefs.edit()
                    .putString(KEY, textToSave)
                    .apply()
            }
        }

        binding.loadButton.setOnClickListener {
            lifecycleScope.launch { updateLoadedText() }
        }
    }

    private suspend fun updateLoadedText() {
        val deferredText = lifecycleScope.async(Dispatchers.IO) {
            sharedPrefs.getString(KEY, "Value Empty")
        }

        binding.loadedTextView.text = deferredText.await()
    }
}