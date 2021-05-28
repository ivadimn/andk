package ru.ivadimn.servicestudy.works

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import ru.ivadimn.servicestudy.R
import ru.ivadimn.servicestudy.databinding.FragmentWorkManagerBinding
import ru.ivadimn.servicestudy.viewBinding

class WorkManagerFragment : Fragment(R.layout.fragment_work_manager) {

    private val binding : FragmentWorkManagerBinding by viewBinding(FragmentWorkManagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadButton.setOnClickListener {
            startDownload()
        }

    }

    private fun startDownload() {
        val urlToDownload = binding.urlEditText.text.toString()

        val workData = workDataOf(
            DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload
        )

        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .build()

       WorkManager.getInstance(requireContext())
            .enqueue(workRequest)

        WorkManager.getInstance(requireContext())
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLifecycleOwner, ::handleWorkInfo)
    }

    private fun handleWorkInfo(workInfo: WorkInfo) {

    }
}