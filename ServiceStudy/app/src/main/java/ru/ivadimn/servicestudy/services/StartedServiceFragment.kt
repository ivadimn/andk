package ru.ivadimn.servicestudy.services

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ivadimn.servicestudy.R
import ru.ivadimn.servicestudy.databinding.FragmentStartedServiceBinding
import ru.ivadimn.servicestudy.viewBinding

class StartedServiceFragment : Fragment(R.layout.fragment_started_service) {

    private val binding : FragmentStartedServiceBinding by viewBinding(FragmentStartedServiceBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.downloadButton.setOnClickListener {
            startService()
        }

        DownloadState.downloadState.onEach {
            binding.downloadButton.isEnabled = !it
            binding.progressBar.isVisible = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun startService() {
        val intent = Intent(requireContext(), DownloadService::class.java)
        requireContext().startService(intent)
    }

}